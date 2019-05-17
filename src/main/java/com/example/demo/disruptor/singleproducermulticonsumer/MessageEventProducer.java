package com.example.demo.disruptor.singleproducermulticonsumer;

import com.lmax.disruptor.RingBuffer;

/**
 * 生产者
 */
public class MessageEventProducer<T> {

    private final RingBuffer<MessageEvent<T>> ringBuffer;

    private String name;

    public MessageEventProducer(String name, RingBuffer<MessageEvent<T>> ringBuffer) {
        this.name = name;
        this.ringBuffer = ringBuffer;
    }

    public void send(T value) {
        long sequence = ringBuffer.next();
        try {
            MessageEvent<T> messageEvent = ringBuffer.get(sequence);
            System.out.println(name + "生产数据:" + value);
            messageEvent.setValue(value);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
