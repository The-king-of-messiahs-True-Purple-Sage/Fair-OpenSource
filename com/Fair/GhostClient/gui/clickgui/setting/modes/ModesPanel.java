/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.gui.clickgui.setting.modes;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.Utils.Render2DUtils;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.ModeSetting;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ModesPanel {
    private boolean hovered = false;
    public String modes;
    private Setting setting;

    public ModesPanel(String modes, Setting setting) {
        this.modes = modes;
        this.setting = setting;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.reload(mouseX, mouseY, particalTicks, x, y, width, height);
    }

    public void reload(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        int color = new Color(7, 7, 7, 213).getRGB();
        if (this.hovered) {
            color = new Color(56, 56, 56, 101).getRGB();
        }
        Render2DUtils.drawRect(x, y, width, height, color);
        Render2DUtils.drawRect(x, y, 2, height, color);
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        if (Click.useFont.getEnable()) {
            if (((ModeSetting)this.setting).getCurrent().equalsIgnoreCase(this.modes)) {
                if (((ModeSetting)this.setting).getCurrent() == "" || ((ModeSetting)this.setting).getCurrent() != this.modes) {
                    ((ModeSetting)this.setting).setCurrent(this.modes);
                }
                Client.cFontRenderer.drawString(this.modes, (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, CategoryPanel.getColor());
            } else {
                Client.cFontRenderer.drawString(this.modes, (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
            }
        } else if (((ModeSetting)this.setting).getCurrent().equalsIgnoreCase(this.modes)) {
            if (((ModeSetting)this.setting).getCurrent() == "" || ((ModeSetting)this.setting).getCurrent() != this.modes) {
                ((ModeSetting)this.setting).setCurrent(this.modes);
            }
            FontUtil.drawString(this.modes, (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, CategoryPanel.getColor());
        } else {
            FontUtil.drawString(this.modes, (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
        }
        GL11.glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            ((ModeSetting)this.setting).setCurrent(this.modes);
            for (Module m : Client.moduleManager.getModules()) {
                m.setMode();
            }
        }
    }
}

