/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.Event.events.callables;

import com.Fair.GhostClient.Event.events.Event;
import com.Fair.GhostClient.Event.events.Typed;

public abstract class EventTyped
implements Event,
Typed {
    private final byte type;

    protected EventTyped(byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}

