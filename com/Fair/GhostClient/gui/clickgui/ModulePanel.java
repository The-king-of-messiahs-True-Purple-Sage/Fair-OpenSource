/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.gui.clickgui;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.gui.clickgui.Events;
import com.Fair.GhostClient.gui.clickgui.setting.EnableSettingPanel;
import com.Fair.GhostClient.gui.clickgui.setting.IntegerSettingPanel;
import com.Fair.GhostClient.gui.clickgui.setting.KeyBindPanel;
import com.Fair.GhostClient.gui.clickgui.setting.ModeSettingPanel;
import com.Fair.GhostClient.gui.clickgui.setting.SettingPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import com.Fair.GhostClient.settings.ModeSetting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ModulePanel
extends Events {
    private Module module;
    private String Title;
    public final List<SettingPanel> settingPanelList = new ArrayList<SettingPanel>();
    private boolean setting = false;
    private boolean temp = false;
    public int BindKey;
    private int opY = 0;
    private static float modsRole;
    private static float modsRoleNow;
    private boolean hovered;
    private boolean hovered1;
    private TimerUtils timerUtils = new TimerUtils();
    private int scroll;
    public static float mRole;
    public static float vRole;

    public ModulePanel(Module module, int offset) {
        this.module = module;
        this.opY = 0;
        if (!module.getSetting().isEmpty()) {
            for (Setting it : module.getSetting()) {
                if (it instanceof EnableSetting) {
                    this.settingPanelList.add(new EnableSettingPanel(it));
                    this.opY += 15;
                    continue;
                }
                if (it instanceof IntegerSetting) {
                    this.settingPanelList.add(new IntegerSettingPanel(it));
                    this.opY += 15;
                    continue;
                }
                if (it instanceof ModeSetting) {
                    this.settingPanelList.add(new ModeSettingPanel(it));
                    this.opY += 15;
                    continue;
                }
                if (!(it instanceof KeyBindSetting)) continue;
                this.settingPanelList.add(new KeyBindPanel(it));
                this.opY += 15;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (SettingPanel settingPanel : this.settingPanelList) {
            settingPanel.keyTyped(typedChar, keyCode);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.hovered1 = ClickGUIUtils.isHovered(mouseX, mouseY, x + width - height + 5, y, height - 5, height);
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        int iy = y;
        int color = new Color(24, 24, 24, 0).getRGB();
        if (this.hovered && !Mouse.isButtonDown((int)0)) {
            color = new Color(2, 2, 2, 151).getRGB();
        }
        int color1 = new Color(24, 24, 24, 50).getRGB();
        if (this.hovered1) {
            color1 = new Color(2, 2, 2, 151).getRGB();
        }
        if (this.module.getState()) {
            color = new Color(31, 31, 31, 196).getRGB();
        }
        ClickGUIUtils.drawRect(x, y, width, height, color);
        if (this.module.getState()) {
            if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawCenteredString(this.module.getName(), x + width / 2, iy + height / 2 - 2, CategoryPanel.color);
            } else {
                FontUtil.drawCenteredString(this.module.getName(), x + width / 2, iy + height / 2 - 4, CategoryPanel.color);
            }
        } else if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawCenteredString(this.module.getName(), x + width / 2, iy + height / 2 - 2, Color.WHITE.getRGB());
        } else {
            FontUtil.drawCenteredString(this.module.getName(), x + width / 2, iy + height / 2 - 4, Color.WHITE.getRGB());
        }
        if (this.setting && !this.settingPanelList.isEmpty()) {
            int settingY = iy;
            for (SettingPanel it : this.settingPanelList) {
                int finalSettingY = settingY;
                it.drawScreen(mouseX, mouseY, particalTicks, x, finalSettingY + height, width, height);
                if (it.isModedr()) {
                    settingY += it.getModeY();
                }
                settingY += height;
            }
        }
    }

    public List<SettingPanel> getSettingPanelList() {
        return this.settingPanelList;
    }

    public boolean isSetting() {
        return this.setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }

    public int getOpY() {
        return this.settingPanelList.size() * 15;
    }

    public void setOpY(int opY) {
        this.opY = opY;
    }

    public int getMaxModule() {
        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<Module> modules = Client.moduleManager.getModules();
        modules.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getName()) - mc.fontRendererObj.getStringWidth(o1.getName()));
        return mc.fontRendererObj.getStringWidth(modules.get(0).getName());
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.hovered && mouseButton == 0 && !Keyboard.isKeyDown((int)42)) {
            this.module.gtoggle();
        }
        if (this.hovered && mouseButton == 1) {
            boolean bl = this.setting = !this.setting;
        }
        if (this.hovered1) {
            // empty if block
        }
        if (this.setting && !this.settingPanelList.isEmpty()) {
            this.settingPanelList.forEach(it -> it.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.setting && !this.settingPanelList.isEmpty()) {
            this.settingPanelList.forEach(it -> it.mouseReleased(mouseX, mouseY, state));
        }
    }
}

