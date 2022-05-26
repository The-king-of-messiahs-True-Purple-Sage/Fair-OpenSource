/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.Utils;

public class TimeUtil {
    private long lastMS = 0L;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasTimeElapsed(long time) {
        return this.time() >= time;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (this.time() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public boolean sleep(long time) {
        if (this.time() >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public boolean hasReached(double milliseconds) {
        return (double)(this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {
        return (float)(this.getTime() - this.lastMS) >= milliSec;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastMS > delay;
    }

    public boolean delay(Double milliSec) {
        return (double)(this.getTime() - this.lastMS) >= milliSec;
    }

    public boolean check(float milliseconds) {
        return (float)this.getTime() >= milliseconds;
    }

    public boolean reach(long time) {
        return this.time() >= time;
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time();
    }
}

