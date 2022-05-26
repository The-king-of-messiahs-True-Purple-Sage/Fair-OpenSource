/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.Event.events.callables;

import com.Fair.GhostClient.Event.events.Cancellable;
import com.Fair.GhostClient.Event.events.Event;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

