
package com.example.demo.notify;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An abstract class for event.
 */
@SuppressWarnings({"PMD.AbstractClassShouldStartWithAbstractNamingRule"})
public abstract class Event implements Serializable {

    private static final long serialVersionUID = -3731383194964997493L;

    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    private final long sequence = SEQUENCE.getAndIncrement();

    /**
     * Event sequence number, which can be used to handle the sequence of events.
     *
     * @return sequence num, It's best to make sure it's monotone.
     */
    public long sequence() {
        return sequence;
    }

    /**
     * Event scope.
     *
     * @return event scope, return null if for all scope
     */
    public String scope() {
        return null;
    }
}

