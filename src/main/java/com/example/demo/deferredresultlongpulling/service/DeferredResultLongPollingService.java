package com.example.demo.deferredresultlongpulling.service;

import com.example.demo.deferredresultlongpulling.vo.LoginResultVo;
import com.example.demo.deferredresultlongpulling.wrapper.DeferredResultWrapper;
import com.example.demo.thread.AbstractTracerRunnable;
import com.example.demo.util.JacksonJsonUtils;
import com.example.demo.util.RestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeferredResultLongPollingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeferredResultLongPollingService.class);

    public static final String LONG_POLLING_HEADER_TIMEOUT = "Long-Pulling-Timeout";

    public static final String LONG_POLLING_HEADER_REQUEST_ID = "Long-Pulling-RequestId";


    private final Multimap<String, DeferredResultWrapper> deferredResults =
            Multimaps.synchronizedSetMultimap(TreeMultimap.create(String.CASE_INSENSITIVE_ORDER, Ordering.natural()));


    private Map<String, LoginResultVo> loginResultVoMap = new ConcurrentHashMap<>();


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
            List<DeferredResultWrapper> deferredResultWrapperList = Lists.newArrayList(deferredResults.get(username));
            if (CollectionUtils.isNotEmpty(deferredResultWrapperList)) {
                deferredResultWrapperList.forEach(deferredResultWrapper -> {
                    LOGGER.info("notify deferredResultWrapper = " + deferredResultWrapper.hashCode());
                    LoginResultVo loginResultVo = new LoginResultVo();
                    loginResultVoMap.put(username, loginResultVo.success());
                    deferredResultWrapper.setResult(loginResultVo.success());
                });
                return true;
            }
        }

        return false;
    }

    public DeferredResultWrapper doPollingLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String longPullingTimeOut = request.getHeader(LONG_POLLING_HEADER_TIMEOUT);
        LOGGER.info("request header Long-Pulling-Timeout = {}", longPullingTimeOut);
        if (StringUtils.isBlank(longPullingTimeOut)) {
            longPullingTimeOut = "30000";
        }
        DeferredResultWrapper deferredResultWrapper = new DeferredResultWrapper(Long.parseLong(longPullingTimeOut));
        LOGGER.info("doPollingLogin deferredResultWrapper = " + deferredResultWrapper.hashCode());

        String json = RestUtil.getData(request);
        ObjectNode node = JacksonJsonUtils.toBean(json, ObjectNode.class);
        if (node == null) {
            LOGGER.info("参数为空");
            return deferredResultWrapper;
        }
        JsonNode jsonNode = node.get("username");
        if (jsonNode == null) {
            LOGGER.info("username 为空");
            return deferredResultWrapper;
        }
        String username = jsonNode.asText();
        deferredResultWrapper.onTimeout(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("time out");
                LoginResultVo loginResultVo = new LoginResultVo();
                deferredResultWrapper.setResult(loginResultVo.fail());
                deferredResults.remove(username, deferredResultWrapper);
            }
        });
        deferredResultWrapper.onCompletion(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("completion");
            }
        });
        LoginResultVo loginResultVo = loginResultVoMap.get(username);
        if (loginResultVo == null) {
            // 等待
            deferredResults.put(username, deferredResultWrapper);
        } else {
            // 直接返回结果
            deferredResultWrapper.setResult(loginResultVo);
        }

        return deferredResultWrapper;
    }
}
