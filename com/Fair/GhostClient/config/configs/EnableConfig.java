/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.EnableSetting;

public class EnableConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "Enable.txt");

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Client.moduleManager.getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof EnableSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((EnableSetting)setting).getEnable();
                    configManager.write(line);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void loadState() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Client.moduleManager.getModules()) {
                    if (module.getSettings().isEmpty()) continue;
                    for (Setting setting : module.getSettings()) {
                        String name = s.split(":")[0];
                        boolean enable = false;
                        String mod = s.split(":")[1];
                        boolean toggled = Boolean.parseBoolean(s.split(":")[2]);
                        if (!setting.getName().equalsIgnoreCase(name) || !module.getName().equalsIgnoreCase(mod)) continue;
                        ((EnableSetting)setting).setEnable(toggled);
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

