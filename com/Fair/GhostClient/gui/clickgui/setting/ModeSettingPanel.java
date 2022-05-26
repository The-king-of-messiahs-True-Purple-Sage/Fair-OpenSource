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
import com.Fair.GhostClient.gui.clickgui.setting.SettingPanel;
import com.Fair.GhostClient.gui.clickgui.setting.modes.ModesPanel;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.ModeSetting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ModeSettingPanel
extends SettingPanel {
    private boolean hovered = false;
    private boolean hovered1 = false;
    public final List<ModesPanel> modesPanelList = new ArrayList<ModesPanel>();

    public List<ModesPanel> getModesPanelList() {
        return this.modesPanelList;
    }

    public ModeSettingPanel(Setting setting) {
        super(setting);
        ModeSetting settingw = (ModeSetting)this.setting;
        if (settingw.getModes().size() > 0) {
            this.modeY += settingw.getModes().size() * 11;
        }
        for (int i = 0; i < settingw.getModes().size(); ++i) {
            this.modesPanelList.add(new ModesPanel(settingw.getModes().get(i), setting));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.reload(mouseX, mouseY, particalTicks, x, y, width - 15, height);
        super.drawScreen(mouseX, mouseY, particalTicks, x, y, width, height);
    }

    public void reload(int mouseX, int mouseY, float particalTicks, int x, int y, int width, int height) {
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, x, y, width, height);
        this.hovered1 = ClickGUIUtils.isHovered(mouseX, mouseY, x + width, y, 15, height);
        int color = new Color(7, 7, 7, 213).getRGB();
        if (this.hovered) {
            color = new Color(7, 7, 7, 213).getRGB();
        }
        int c = new Color(7, 7, 7, 213).getRGB();
        if (this.hovered1) {
            c = new Color(28, 28, 28, 213).getRGB();
        }
        Render2DUtils.drawRect(x, y, width, height, color);
        Render2DUtils.drawRect(x, y, 2, height, color);
        Render2DUtils.drawRect(x + width, y, 15, height, c);
        ModeSetting settingw = (ModeSetting)this.setting;
        if (this.modedr) {
            FontUtil.drawString("-", x + width + 15 - FontUtil.getStringWidth("-") - 4, y + height / 2 - 4, Color.WHITE.getRGB());
            int modesPanelY = y + height;
            int modesPanelY1 = 0;
            for (ModesPanel modesPanel : this.modesPanelList) {
                modesPanelY1 += 11;
            }
            ClickGUIUtils.drawRect(x, y + 14, width + 15, modesPanelY1, new Color(7, 7, 7, 213).getRGB());
            for (ModesPanel modesPanel : this.modesPanelList) {
                modesPanel.drawScreen(mouseX, mouseY, particalTicks, x, modesPanelY, width + 15, 10);
                modesPanelY += 11;
            }
        } else {
            FontUtil.drawString("+", x + width + 15 - FontUtil.getStringWidth("+") - 4, y + height / 2 - 4, Color.WHITE.getRGB());
        }
        for (ModesPanel modesPanel : this.modesPanelList) {
            if (!((ModeSetting)this.setting).getCurrent().equalsIgnoreCase(modesPanel.modes) || ((ModeSetting)this.setting).getCurrent() != "" && ((ModeSetting)this.setting).getCurrent() == modesPanel.modes) continue;
            ((ModeSetting)this.setting).setCurrent(modesPanel.modes);
        }
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawString(this.setting.getName() + ": " + ((ModeSetting)this.setting).getCurrent(), (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
        } else {
            FontUtil.drawString(this.setting.getName() + ": " + ((ModeSetting)this.setting).getCurrent(), (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
        }
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hovered1) {
            this.modedr = !this.modedr;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseClicked1(int mouseX, int mouseY, int mouseButton) {
        for (ModesPanel modesPanel : this.modesPanelList) {
            modesPanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked1(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}

