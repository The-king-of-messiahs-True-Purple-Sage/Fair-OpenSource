/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.gui.clickgui.setting;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.Utils.Render2DUtils;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.gui.clickgui.setting.SettingPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.awt.Color;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class KeyBindPanel
extends SettingPanel {
    private boolean hovered = false;
    private int BindKey = 0;
    private Module module;

    public KeyBindPanel(Setting setting) {
        super(setting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.reload(mouseX, mouseY, particalTicks, x, y, width, height);
        super.drawScreen(mouseX, mouseY, particalTicks, x, y, width, height);
    }

    public void reload(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        int color = new Color(7, 7, 7, 213).getRGB();
        if (this.hovered) {
            color = new Color(7, 7, 7, 213).getRGB();
        }
        Render2DUtils.drawRect(x, y, width, height, color);
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        if (((KeyBindSetting)this.setting).getModule().getKey() == 0) {
            if (this.aBinding) {
                if (Click.useFont.getEnable()) {
                    Client.cFontRenderer.drawString("Press a key", (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
                } else {
                    FontUtil.drawString("Press a key", (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
                }
            } else if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawString("KeyBind : None", (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
            } else {
                FontUtil.drawString("KeyBind : None", (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
            }
        } else if (this.aBinding) {
            if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawString("Press a key", (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, CategoryPanel.color);
            } else {
                FontUtil.drawString("Press a key", (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, CategoryPanel.color);
            }
        } else if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawString("KeyBind : " + Keyboard.getKeyName((int)((KeyBindSetting)this.setting).getModule().getKey()), (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, CategoryPanel.color);
        } else {
            FontUtil.drawString("KeyBind : " + Keyboard.getKeyName((int)((KeyBindSetting)this.setting).getModule().getKey()), (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, CategoryPanel.color);
        }
        GL11.glPopMatrix();
        if (!this.aBinding) {
            return;
        }
        a = true;
        if (Code != 0) {
            ((KeyBindSetting)this.setting).getModule().setKey(Code);
            this.aBinding = false;
            a = false;
            KeyBindPanel.setCode(0);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (a) {
            KeyBindPanel.setCode(keyCode);
        } else {
            KeyBindPanel.setCode(0);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered) {
            this.aBinding = !this.aBinding;
        } else if (!this.hovered && ((KeyBindSetting)this.setting).getModule().getKey() != 0 && this.aBinding) {
            this.aBinding = false;
            ((KeyBindSetting)this.setting).getModule().setKey(0);
        } else {
            this.aBinding = false;
        }
    }
}

