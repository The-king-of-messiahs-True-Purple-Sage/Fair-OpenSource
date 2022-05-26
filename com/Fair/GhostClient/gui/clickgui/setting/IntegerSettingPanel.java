/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.gui.clickgui.setting;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.Utils.Render2DUtils;
import com.Fair.GhostClient.gui.clickgui.setting.SettingPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.IntegerSetting;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class IntegerSettingPanel
extends SettingPanel {
    private boolean hovered = false;
    private boolean silderHovered = false;
    private boolean dragging = false;
    private double offsetX = 0.0;

    public IntegerSettingPanel(Setting setting) {
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
            color = new Color(2, 2, 2, 213).getRGB();
        }
        Render2DUtils.drawRect(x, y, width, height, color);
        IntegerSetting setting = (IntegerSetting)this.setting;
        double percentBar = (setting.getCurrent() - setting.getMin()) / (setting.getMax() - setting.getMin());
        if (this.dragging) {
            for (Module m : Client.moduleManager.getModules()) {
                m.isSilder();
            }
            double value = setting.getMax() - setting.getMin();
            double val = setting.getMin() + MathHelper.clamp_double((double)((double)(mouseX - x) / (double)width), (double)0.0, (double)1.0) * value;
            DecimalFormat df = new DecimalFormat("#.00");
            String str = df.format(val);
            Double retn = new Double(str);
            setting.setCurrent(retn);
        }
        Gui.drawRect((int)x, (int)y, (int)(x + (int)(percentBar * (double)width)), (int)(y + 15), (int)new Color((int)Click.red.getCurrent(), (int)Click.green.getCurrent(), (int)Click.blue.getCurrent(), 33).getRGB());
        if (percentBar > 0.0 && percentBar < 1.0) {
            Gui.drawRect((int)(x + (int)(percentBar * (double)width) - 1), (int)y, (int)(x + (int)Math.min(percentBar * (double)width, (double)width)), (int)(y + 15), (int)new Color((int)Click.red.getCurrent(), (int)Click.green.getCurrent(), (int)Click.blue.getCurrent(), 40).getRGB());
        }
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawString(setting.getName(), (float)(x + 3) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
            Client.cFontRenderer.drawString("" + ((IntegerSetting)this.setting).getCurrent(), (float)(x + width - Client.cFontRenderer.getStringWidth("" + ((IntegerSetting)this.setting).getCurrent()) - 2) * 1.3333334f, (float)(y + height / 2 - 2) * 1.3333334f, Color.WHITE.getRGB());
        } else {
            FontUtil.drawString(setting.getName(), (float)(x + 4) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
            FontUtil.drawString("" + ((IntegerSetting)this.setting).getCurrent(), (float)(x + width - FontUtil.getStringWidth("" + ((IntegerSetting)this.setting).getCurrent())) * 1.3333334f, (float)(y + height / 2 - 3) * 1.3333334f, Color.WHITE.getRGB());
        }
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hovered && mouseButton == 0) {
            this.dragging = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }
}

