

package com.example.demo.notify.event;

import com.example.demo.notify.Event;

import java.util.List;

/**
 * LocalDataChangeEvent.
 */
public class LocalDataChangeEvent extends Event {

    public final String groupKey;

    public final boolean isBeta;

    public final List<String> betaIps;

    public final String tag;

    public LocalDataChangeEvent(String groupKey) {
        this.groupKey = groupKey;
        this.isBeta = false;
        this.betaIps = null;
        this.tag = null;
    }

    public LocalDataChangeEvent(String groupKey, boolean isBeta, List<String> betaIps) {
        this.groupKey = groupKey;
        this.isBeta = isBeta;
        this.betaIps = betaIps;
        this.tag = null;
    }

    public LocalDataChangeEvent(String groupKey, boolean isBeta, List<String> betaIps, String tag) {
        this.groupKey = groupKey;
        this.isBeta = isBeta;
        this.betaIps = betaIps;
        this.tag = tag;
    }
}
