/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient;

import com.Fair.GhostClient.modules.Module;

public class Setting {
    public final String name;
    private Module parent;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Module getParentMod() {
        return this.parent;
    }
}

