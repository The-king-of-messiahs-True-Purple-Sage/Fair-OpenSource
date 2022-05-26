/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;

public class KeyBindConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "KeyBind.txt");

    public static void saveKey() {
        try {
            configManager.clear();
            for (Module module : Client.moduleManager.getModules()) {
                String line = module.getName() + ":" + module.getKey();
                configManager.write(line);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void loadKey() {
        try {
            for (String s : configManager.read()) {
                for (Module module : Client.moduleManager.getModules()) {
                    String name = s.split(":")[0];
                    int key = Integer.parseInt(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name)) continue;
                    module.setKey(key);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

