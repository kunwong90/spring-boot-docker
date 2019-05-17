package com.example.demo.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event + ",sequence = " + ",endOfBatch = " + endOfBatch);
    }
}