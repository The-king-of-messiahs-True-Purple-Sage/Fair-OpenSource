/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;

public class ModuleConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "modules.txt");

    public static void loadModules() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Client.moduleManager.getModules()) {
                    String name = s.split(":")[0];
                    boolean toggled = Boolean.parseBoolean(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name) || module.getName() == "ClickGUI") continue;
                    module.setState(toggled, true);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveModules() {
        try {
            configManager.clear();
            for (Module module : Client.moduleManager.getModules()) {
                if (module.getName() == "ClickGUI") continue;
                String line = module.getName() + ":" + String.valueOf(module.getState());
                configManager.write(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

