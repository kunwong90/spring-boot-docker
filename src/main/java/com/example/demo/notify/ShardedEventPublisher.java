
package com.example.demo.notify;


import com.example.demo.notify.listener.Subscriber;

/**
 * Sharded event publisher.
 *
 * <p>To support one publisher for different events.
 */
public interface ShardedEventPublisher extends EventPublisher {

    /**
     * Add listener for default share publisher.
     *
     * @param subscriber    {@link Subscriber}
     * @param subscribeType subscribe event type, such as slow event or general event.
     */
    void addSubscriber(Subscriber subscriber, Class<? extends Event> subscribeType);

    /**
     * Remove listener for default share publisher.
     *
     * @param subscriber    {@link Subscriber}
     * @param subscribeType subscribe event type, such as slow event or general event.
     */
    void removeSubscriber(Subscriber subscriber, Class<? extends Event> subscribeType);
}
