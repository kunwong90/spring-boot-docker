package com.example.demo.disruptor.singleproducermulticonsumer;

import com.lmax.disruptor.WorkHandler;

/**
 * 消费者
 * @param <T>
 */
public class MessageEventConsumer<T> implements WorkHandler<MessageEvent<T>> {

    private String name;

    public MessageEventConsumer(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(MessageEvent<T> event) throws Exception {
        System.out.println(name + "consumer data = " + event.getValue());
    }
}
