/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.modules.configs;

import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.config.configs.EnableConfig;
import com.Fair.GhostClient.config.configs.IntegerConfig;
import com.Fair.GhostClient.config.configs.KeyBindConfig;
import com.Fair.GhostClient.config.configs.ModeConfig;
import com.Fair.GhostClient.config.configs.ModuleConfig;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.io.File;

public class SaveConfig
extends Module {
    private TimerUtils timerUtils;
    private boolean kp;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public SaveConfig() {
        super("SaveConfig", 0, Category.Configs, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        super.enable();
        this.kp = true;
        this.gtoggle();
        try {
            File ConfigDir = new File(Tools.getConfigPath());
            if (!ConfigDir.exists()) {
                ConfigDir.mkdir();
            }
            EnableConfig.saveState();
            IntegerConfig.saveState();
            KeyBindConfig.saveKey();
            ModeConfig.saveState();
            ModuleConfig.saveModules();
        }
        catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}

