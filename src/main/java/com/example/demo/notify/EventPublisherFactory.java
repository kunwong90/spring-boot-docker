package com.example.demo.notify;

import java.util.function.BiFunction;

/**
 * Event publisher factory.
 */
public interface EventPublisherFactory extends BiFunction<Class<? extends Event>, Integer, EventPublisher> {

    /**
     * Build an new {@link EventPublisher}.
     *
     * @param eventType    eventType for {@link EventPublisher}
     * @param maxQueueSize max queue size for {@link EventPublisher}
     * @return new {@link EventPublisher}
     */
    @Override
    EventPublisher apply(Class<? extends Event> eventType, Integer maxQueueSize);
}
