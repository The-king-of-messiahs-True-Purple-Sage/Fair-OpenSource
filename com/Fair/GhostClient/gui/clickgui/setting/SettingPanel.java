/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.gui.clickgui.setting;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.util.ArrayList;

public class SettingPanel {
    public final Setting setting;
    public static KeyBindSetting settings;
    private Module module;
    public boolean aBinding;
    public static boolean a;
    public static int Code;
    public static Setting set;
    public int modeY;
    public boolean modedr;

    public SettingPanel(Setting setting) {
        this.setting = setting;
    }

    public boolean isModedr() {
        return this.modedr;
    }

    public void setModedr(boolean modedr) {
        this.modedr = modedr;
    }

    public int getModeY() {
        return this.modeY;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
    }

    public void mouseClicked1(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public static void setCode(int code) {
        Code = code;
    }

    public int getMaxSetting() {
        ArrayList settings = new ArrayList();
        Client.moduleManager.getModules().forEach(it -> it.getSetting().forEach(s -> settings.addAll(it.getSetting())));
        settings.sort((o1, o2) -> FontUtil.getStringWidth(o2.getName()) - FontUtil.getStringWidth(o1.getName()));
        return FontUtil.getStringWidth(((Setting)settings.get(0)).getName()) + 50;
    }
}

