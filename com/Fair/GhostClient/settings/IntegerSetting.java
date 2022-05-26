/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.settings;

import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.modules.Module;

public class IntegerSetting
extends Setting {
    private final double min;
    private final double max;
    private double current;
    private Module parent;

    public IntegerSetting(String name, double current, double min, double max) {
        super(name);
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public double getCurrent() {
        return this.current;
    }

    @Override
    public Module getParentMod() {
        return this.parent;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }
}

