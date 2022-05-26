/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.settings;

import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.modules.Module;

public class KeyBindSetting
extends Setting {
    private int keyCode;
    private Module module;

    public KeyBindSetting(String name, int keyCode, Module module) {
        super(name);
        this.keyCode = keyCode;
        this.module = module;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}

