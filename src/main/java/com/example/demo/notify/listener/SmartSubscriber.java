

package com.example.demo.notify.listener;

import com.example.demo.notify.Event;

import java.util.List;

/**
 * Subscribers to multiple events can be listened to.
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class SmartSubscriber extends Subscriber {

    /**
     * Returns which event type are smartsubscriber interested in.
     *
     * @return The interestd event types.
     */
    public abstract List<Class<? extends Event>> subscribeTypes();

    @Override
    public final Class<? extends Event> subscribeType() {
        return null;
    }

    @Override
    public final boolean ignoreExpireEvent() {
        return false;
    }
}
