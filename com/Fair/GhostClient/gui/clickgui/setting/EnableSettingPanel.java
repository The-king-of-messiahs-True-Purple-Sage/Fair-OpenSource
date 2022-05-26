/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.EnableSetting;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class EnableSettingPanel
extends SettingPanel {
    private boolean hovered = false;

    public EnableSettingPanel(Setting setting) {
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
        Render2DUtils.drawRect(x + 1, y + 2, height - 6, height - 6, -16777216);
        if (((EnableSetting)this.setting).getEnable()) {
            Render2DUtils.drawRect(x + 2, y + 3, height - 7, height - 7, new Color(CategoryPanel.getR(), CategoryPanel.getG(), CategoryPanel.getB(), 100).getRGB());
        }
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        if (((EnableSetting)this.setting).getEnable()) {
            if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawString(this.setting.getName(), (float)(x + 14) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, CategoryPanel.color);
            } else {
                FontUtil.drawString(this.setting.getName(), (float)(x + 14) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, CategoryPanel.color);
            }
        } else if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawString(this.setting.getName(), (float)(x + 14) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
        } else {
            FontUtil.drawString(this.setting.getName(), (float)(x + 14) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
        }
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            ((EnableSetting)this.setting).setEnable(!((EnableSetting)this.setting).getEnable());
        }
    }
}

