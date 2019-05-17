package com.example.demo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * In order to allow the Disruptor to preallocate these events for us, we need to an EventFactory that will perform the construction
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}