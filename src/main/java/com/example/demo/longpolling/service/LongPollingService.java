package com.example.demo.longpolling.service;

import com.example.demo.longpolling.event.LoginEvent;
import com.example.demo.longpolling.vo.LoginResultVo;
import com.example.demo.notify.Event;
import com.example.demo.notify.NotifyCenter;
import com.example.demo.notify.listener.Subscriber;
import com.example.demo.thread.AbstractTracerRunnable;
import com.example.demo.util.ConfigExecutor;
import com.example.demo.util.JacksonJsonUtils;
import com.example.demo.util.RequestUtil;
import com.example.demo.util.RestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class LongPollingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongPollingService.class);

    public static final String LONG_POLLING_HEADER_TIMEOUT = "Long-Pulling-Timeout";

    public static final String LONG_POLLING_HEADER_REQUEST_ID = "Long-Pulling-RequestId";


   /* private final Multimap<String, LoginResultVo> loginResults =
            Multimaps.synchronizedSetMultimap(TreeMultimap.create(String.CASE_INSENSITIVE_ORDER, Ordering.natural()));*/


    private final Multimap<String, ClientLongPolling> clientLongPollingMultimap = Multimaps.synchronizedMultimap(HashMultimap.create());


    private final Map<String, LoginResultVo> loginResultVoMap = new ConcurrentHashMap<>();

    private final Map<String, ClientLongPolling> clientLongPollingMap = new ConcurrentHashMap<>();


    private LongPollingService() {


        NotifyCenter.registerToPublisher(LoginEvent.class, NotifyCenter.ringBufferSize);

        // Register A Subscriber to subscribe LocalDataChangeEvent.
        NotifyCenter.registerSubscriber(new Subscriber<LoginEvent>() {

            @Override
            public void onEvent(LoginEvent event) {
                ConfigExecutor.executeLongPolling(new DataChangeTask(event.getUsername()));
            }

            @Override
            public Class<? extends Event> subscribeType() {
                return LoginEvent.class;
            }
        });
    }

    public boolean notify(HttpServletRequest request) throws IOException {
        String json = RestUtil.getData(request);
        ObjectNode node = JacksonJsonUtils.toBean(json, ObjectNode.class);
        if (node == null) {
            return false;
        }
        JsonNode jsonNode = node.get("username");
        if (jsonNode == null) {
            return false;
        }
        String username = jsonNode.asText();
        if (StringUtils.isNotBlank(username)) {
            return NotifyCenter.publishEvent(new LoginEvent(username));
        }
        return false;
    }

    public void doPollingLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestId = request.getHeader(LONG_POLLING_HEADER_REQUEST_ID);
        LOGGER.info("requestId = {}", requestId);
        String json = RestUtil.getData(request);
        ObjectNode node = JacksonJsonUtils.toBean(json, ObjectNode.class);
        if (node == null) {
            return;
        }
        JsonNode jsonNode = node.get("username");
        if (jsonNode == null) {
            return;
        }
        String username = jsonNode.asText();
        String longPullingTimeOut = request.getHeader(LONG_POLLING_HEADER_TIMEOUT);
        LOGGER.info("request header Long-Pulling-Timeout = {}", longPullingTimeOut);
        if (StringUtils.isBlank(longPullingTimeOut)) {
            longPullingTimeOut = "30000";
        }

        long timeOut = Long.parseLong(longPullingTimeOut) - 500;
        if (timeOut < 29500) {
            timeOut = 29500;
        }
        LoginResultVo resultVo = loginResultVoMap.get(username);
        if (resultVo != null) {
            LOGGER.info("check success,result = {}", JacksonJsonUtils.toJson(resultVo));
            generateResponse(response, resultVo);
            return;
        }

        String ip = RequestUtil.getRemoteIp(request);

        // Must be called by http thread, or send response.
        final AsyncContext asyncContext = request.startAsync();

        // AsyncContext.setTimeout() is incorrect, Control by oneself
        asyncContext.setTimeout(0L);
        ClientLongPolling clientLongPolling = new ClientLongPolling(asyncContext, ip, timeOut, username);
        clientLongPollingMultimap.put(username, clientLongPolling);

        ConfigExecutor.executeLongPolling(clientLongPolling);
    }

    void generateResponse(HttpServletResponse response, LoginResultVo loginResultVo) {
        if (null == loginResultVo) {
            return;
        }

        try {
            // Disable cache.
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JacksonJsonUtils.toJson(loginResultVo));
        } catch (Exception ex) {
            LOGGER.error(ex.toString(), ex);
        }
    }

    class DataChangeTask extends AbstractTracerRunnable {
        final String username;

        DataChangeTask(String username) {
            this.username = username;
        }

        @Override
        public void runWithMDC() {
            List<ClientLongPolling> results = Lists.newArrayList(clientLongPollingMultimap.get(username));
            if (CollectionUtils.isNotEmpty(results)) {
                results.forEach(clientLongPolling -> {
                    LoginResultVo loginResultVo = new LoginResultVo();
                    loginResultVo.setMsg("登录成功");
                    loginResultVo.setStatus(HttpServletResponse.SC_OK);
                    loginResultVoMap.put(username, loginResultVo);
                    clientLongPolling.sendResponse(loginResultVo);
                });
                clientLongPollingMultimap.removeAll(username);
            } else {
                LOGGER.info("username = {} 没有长轮询请求", username);
            }
        }
    }


    class ClientLongPolling implements Runnable {


        private final AsyncContext asyncContext;

        private final String username;


        private final long createTime;

        private final String ip;


        private final long timeoutTime;

        Future<?> asyncTimeoutFuture;

        ClientLongPolling(AsyncContext ac, String ip, long timeoutTime, String username) {
            this.asyncContext = ac;
            this.createTime = System.currentTimeMillis();
            this.ip = ip;
            this.timeoutTime = timeoutTime;
            this.username = username;
        }

        @Override
        public void run() {
            asyncTimeoutFuture = ConfigExecutor.scheduleLongPolling(new AbstractTracerRunnable() {
                @Override
                public void runWithMDC() {
                    try {
                        //create a new list to avoid ConcurrentModificationException
                        List<ClientLongPolling> results = Lists.newArrayList(clientLongPollingMultimap.get(username));
                        if (CollectionUtils.isNotEmpty(results)) {
                            LOGGER.info(JacksonJsonUtils.toJson(results));
                            LoginResultVo loginResultVo = loginResultVoMap.get(username);
                            LoginResultVo finalLoginResultVo;
                            if (loginResultVo == null) {
                                loginResultVo = new LoginResultVo();
                                loginResultVo.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                                loginResultVo.setMsg("未登录");
                                finalLoginResultVo = loginResultVo;
                            } else {
                                finalLoginResultVo = loginResultVo;
                            }
                            results.forEach(clientLongPolling -> clientLongPolling.sendResponse(finalLoginResultVo));
                            clientLongPollingMultimap.removeAll(username);
                        } else {
                            LOGGER.warn("client subsciber's relations delete fail.");
                        }
                    } catch (Throwable t) {
                        LOGGER.error("long polling error:" + t.getMessage(), t.getCause());
                    }
                }
            }, timeoutTime, TimeUnit.MILLISECONDS);
        }

        void sendResponse(LoginResultVo loginResultVo) {
            // Cancel time out task.
            if (null != asyncTimeoutFuture) {
                boolean result = asyncTimeoutFuture.cancel(false);
                LOGGER.info("cancel future result = {}", result);
            }
            generateResponse(loginResultVo);
        }

        void generateResponse(LoginResultVo loginResultVo) {
            if (null == loginResultVo) {
                // Tell web container to send http response.
                asyncContext.complete();
                return;
            }

            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

            try {
                // Disable cache.
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                response.setHeader("Cache-Control", "no-cache,no-store");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(JacksonJsonUtils.toJson(loginResultVo));
            } catch (Exception ex) {
                LOGGER.error(ex.toString(), ex);
            } finally {
                asyncContext.complete();
            }
        }

        public String getUsername() {
            return username;
        }

        public long getCreateTime() {
            return createTime;
        }

        public String getIp() {
            return ip;
        }

        public long getTimeoutTime() {
            return timeoutTime;
        }

        public Future<?> getAsyncTimeoutFuture() {
            return asyncTimeoutFuture;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ClientLongPolling{");
            sb.append("username='").append(username).append('\'');
            sb.append(", createTime=").append(createTime);
            sb.append(", ip='").append(ip).append('\'');
            sb.append(", timeoutTime=").append(timeoutTime);
            sb.append('}');
            return sb.toString();
        }
    }
}
