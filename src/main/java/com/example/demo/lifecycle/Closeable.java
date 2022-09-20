
package com.example.demo.lifecycle;

/**
 * An interface is used to define the resource's close and shutdown, such as IO Connection and ThreadPool.
 */
public interface Closeable {

    /**
     * Shutdown the Resources, such as Thread Pool.
     */
    void shutdown() throws Exception;

}
