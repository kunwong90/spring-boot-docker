package com.example.demo.notify;

import com.example.demo.lifecycle.Closeable;
import com.example.demo.notify.listener.Subscriber;

/**
 * Event publisher.
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 * @author zongtanghu
 */
public interface EventPublisher extends Closeable {

    /**
     * Initializes the event publisher.
     *
     * @param type       {@link Event >}
     * @param bufferSize Message staging queue size
     */
    void init(Class<? extends Event> type, int bufferSize);

    /**
     * The number of currently staged events.
     *
     * @return event size
     */
    long currentEventSize();

    /**
     * Add listener.
     *
     * @param subscriber {@link Subscriber}
     */
    void addSubscriber(Subscriber subscriber);

    /**
     * Remove listener.
     *
     * @param subscriber {@link Subscriber}
     */
    void removeSubscriber(Subscriber subscriber);

    /**
     * publish event.
     *
     * @param event {@link Event}
     * @return publish event is success
     */
    boolean publish(Event event);

    /**
     * Notify listener.
     *
     * @param subscriber {@link Subscriber}
     * @param event      {@link Event}
     */
    void notifySubscriber(Subscriber subscriber, Event event);

}
