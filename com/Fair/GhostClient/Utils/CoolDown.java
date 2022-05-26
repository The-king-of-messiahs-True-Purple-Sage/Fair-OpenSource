/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.Utils;

public class CoolDown {
    private long start;
    private long lasts;

    public CoolDown(long lasts) {
        this.lasts = lasts;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed() {
        return System.currentTimeMillis() >= this.start + this.lasts;
    }

    public void setCooldown(long time) {
        this.lasts = time;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.start;
    }

    public long getTimeLeft() {
        return this.lasts - (System.currentTimeMillis() - this.start);
    }
}

