/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.modules.configs;

import com.Fair.GhostClient.config.configs.EnableConfig;
import com.Fair.GhostClient.config.configs.IntegerConfig;
import com.Fair.GhostClient.config.configs.KeyBindConfig;
import com.Fair.GhostClient.config.configs.ModeConfig;
import com.Fair.GhostClient.config.configs.ModuleConfig;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;

public class ReloadConfig
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public ReloadConfig() {
        super("ReloadConfig", 0, Category.Configs, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        super.enable();
        this.gtoggle();
        try {
            IntegerConfig.loadState();
            EnableConfig.loadState();
            ModeConfig.loadState();
            KeyBindConfig.loadKey();
            ModuleConfig.loadModules();
            this.isSilder();
            this.setMode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

