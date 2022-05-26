/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.gui.clickgui.setting.modes.ModesPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.ModeSetting;
import java.util.ArrayList;
import java.util.List;

public class ModeConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "Mode.txt");
    public static final List<ModesPanel> modesPanelList = new ArrayList<ModesPanel>();

    public static void saveState() {
        try {
            configManager.clear();
            for (Module module : Client.moduleManager.getModules()) {
                if (module.getSettings().isEmpty()) continue;
                for (Setting setting : module.getSettings()) {
                    if (!(setting instanceof ModeSetting)) continue;
                    String line = setting.getName() + ":" + module.getName() + ":" + ((ModeSetting)setting).getCurrent();
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
                        String value = s.split(":")[2];
                        if (!setting.getName().equalsIgnoreCase(name) || !((ModeSetting)setting).getParent().getName().equalsIgnoreCase(mod)) continue;
                        ((ModeSetting)setting).setCurrent(value);
                        for (int i = 0; i < ((ModeSetting)setting).getModes().size(); ++i) {
                            modesPanelList.add(new ModesPanel(((ModeSetting)setting).getModes().get(i), setting));
                        }
                        module.setMode();
                        for (ModesPanel modesPanel : modesPanelList) {
                            if (!((ModeSetting)setting).getCurrent().equalsIgnoreCase(modesPanel.modes) || ((ModeSetting)setting).getCurrent() != "" && ((ModeSetting)setting).getCurrent() == modesPanel.modes) continue;
                            ((ModeSetting)setting).setCurrent(modesPanel.modes);
                        }
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

