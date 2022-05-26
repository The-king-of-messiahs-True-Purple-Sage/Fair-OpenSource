/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;

public class IntegerConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "Integer.txt");

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Client.moduleManager.getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof IntegerSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((IntegerSetting)setting).getCurrent();
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
                        String mod = s.split(":")[1];
                        double value = Double.parseDouble(s.split(":")[2]);
                        if (setting.getName().equalsIgnoreCase(name) && module.getName().equalsIgnoreCase(mod)) {
                            ((IntegerSetting)setting).setCurrent(value);
                        }
                        module.isSilder();
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

