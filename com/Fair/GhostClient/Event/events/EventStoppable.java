/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.Event.events;

import com.Fair.GhostClient.Event.events.Event;

public abstract class EventStoppable
implements Event {
    private boolean stopped;

    protected EventStoppable() {
    }

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}

