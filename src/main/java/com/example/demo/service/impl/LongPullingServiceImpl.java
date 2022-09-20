package com.example.demo.service.impl;

import com.example.demo.notify.Event;
import com.example.demo.notify.NotifyCenter;
import com.example.demo.notify.event.LocalDataChangeEvent;
import com.example.demo.notify.listener.Subscriber;
import com.example.demo.util.ConfigExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LongPullingServiceImpl {

    /**
     * ClientLongPolling subscibers.
     */
    private final Queue<ClientLongPolling> allSubs;

    private AsyncContext asyncContext;


    public void doPolling(HttpServletRequest request) {
        this.asyncContext = request.startAsync();
        ConfigExecutor.executeLongPolling(
                new ClientLongPolling(asyncContext, new HashMap<>(), "", 1, 295000, "", ""));
    }


    public LongPullingServiceImpl() {
        this.allSubs = new ConcurrentLinkedQueue<>();
        NotifyCenter.registerToPublisher(LocalDataChangeEvent.class, NotifyCenter.ringBufferSize);

        // Register A Subscriber to subscribe LocalDataChangeEvent.
        NotifyCenter.registerSubscriber(new Subscriber() {

            @Override
            public void onEvent(Event event) {

                if (event instanceof LocalDataChangeEvent) {
                    LocalDataChangeEvent evt = (LocalDataChangeEvent) event;
                    ConfigExecutor.executeLongPolling(new DataChangeTask(evt.groupKey, evt.isBeta, evt.betaIps));
                }

            }

            @Override
            public Class<? extends Event> subscribeType() {
                return LocalDataChangeEvent.class;
            }
        });
    }

    class ClientLongPolling implements Runnable {

        @Override
        public void run() {
            asyncTimeoutFuture = ConfigExecutor.scheduleLongPolling(() -> {
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                try {
                    // Disable cache.
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);
                    response.setHeader("Cache-Control", "no-cache,no-store");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("current date = " + new Date());
                    asyncContext.complete();
                } catch (Exception ex) {
                    asyncContext.complete();
                }

            }, timeoutTime, TimeUnit.MILLISECONDS);

            allSubs.add(this);
        }

        void sendResponse(List<String> changedGroups) {

            // Cancel time out task.
            if (null != asyncTimeoutFuture) {
                asyncTimeoutFuture.cancel(false);
            }
            generateResponse(changedGroups);
        }

        void generateResponse(List<String> changedGroups) {
            if (null == changedGroups) {

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
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("success");
                asyncContext.complete();
            } catch (Exception ex) {
                asyncContext.complete();
            }
        }

        ClientLongPolling(AsyncContext ac, Map<String, String> clientMd5Map, String ip, int probeRequestSize,
                          long timeoutTime, String appName, String tag) {
            this.asyncContext = ac;
            this.clientMd5Map = clientMd5Map;
            this.probeRequestSize = probeRequestSize;
            this.createTime = System.currentTimeMillis();
            this.ip = ip;
            this.timeoutTime = timeoutTime;
            this.appName = appName;
            this.tag = tag;
        }

        final AsyncContext asyncContext;

        final Map<String, String> clientMd5Map;

        final long createTime;

        final String ip;

        final String appName;

        final String tag;

        final int probeRequestSize;

        final long timeoutTime;

        Future<?> asyncTimeoutFuture;

        @Override
        public String toString() {
            return "ClientLongPolling{" + "clientMd5Map=" + clientMd5Map + ", createTime=" + createTime + ", ip='" + ip
                    + '\'' + ", appName='" + appName + '\'' + ", tag='" + tag + '\'' + ", probeRequestSize="
                    + probeRequestSize + ", timeoutTime=" + timeoutTime + '}';
        }
    }

    class DataChangeTask implements Runnable {

        @Override
        public void run() {
            try {

                for (Iterator<ClientLongPolling> iter = allSubs.iterator(); iter.hasNext(); ) {
                    ClientLongPolling clientSub = iter.next();
                    iter.remove(); // Delete subscribers' relationships.

                    clientSub.sendResponse(Arrays.asList(groupKey));
                }

            } catch (Throwable t) {

            }
        }

        DataChangeTask(String groupKey, boolean isBeta, List<String> betaIps) {
            this(groupKey, isBeta, betaIps, null);
        }

        DataChangeTask(String groupKey, boolean isBeta, List<String> betaIps, String tag) {
            this.groupKey = groupKey;
            this.isBeta = isBeta;
            this.betaIps = betaIps;
            this.tag = tag;
        }

        final String groupKey;

        final long changeTime = System.currentTimeMillis();

        final boolean isBeta;

        final List<String> betaIps;

        final String tag;
    }
}
