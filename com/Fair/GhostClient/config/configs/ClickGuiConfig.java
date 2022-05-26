/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.config.configs;

import com.Fair.GhostClient.config.ConfigManager;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.gui.clickgui.ClickGUI;
import com.Fair.GhostClient.modules.Tools;

public class ClickGuiConfig {
    private static ConfigManager configManager = new ConfigManager(Tools.getConfigPath(), "clickgui.txt");

    public static void loadClickGui() {
        for (String s : configManager.read()) {
            String panelName = s.split(":")[0];
            float panelCoordX = Float.parseFloat(s.split(":")[1]);
            float panelCoordY = Float.parseFloat(s.split(":")[2]);
            boolean extended = Boolean.parseBoolean(s.split(":")[3]);
            for (CategoryPanel categoryPanel : ClickGUI.categoryPanels) {
                if (!categoryPanel.category.name().equalsIgnoreCase(panelName)) continue;
                categoryPanel.setX((int)panelCoordX);
                categoryPanel.setY((int)panelCoordY);
                categoryPanel.setDisplayModulePanel(extended);
            }
        }
    }

    public static void saveClickGui() {
        try {
            configManager.clear();
            for (CategoryPanel categoryPanel : ClickGUI.categoryPanels) {
                configManager.write(categoryPanel.category.name() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + ":" + categoryPanel.isDisplayModulePanel());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

