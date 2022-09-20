
package com.example.demo.notify;

/**
 * This event share one event-queue.
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class SlowEvent extends Event {

    @Override
    public long sequence() {
        return 0;
    }
}