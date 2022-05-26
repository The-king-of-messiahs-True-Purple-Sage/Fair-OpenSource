/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.gui.clickgui.guis;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.FontLoaders;
import com.Fair.GhostClient.Utils.HUDUtils;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.EnableSetting;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class Gui
extends GuiScreen {
    public static float windowX = 0.0f;
    public static float windowY = 0.0f;
    public static float width = 500.0f;
    public static float height = 300.0f;
    public static Module currentMod;
    public static Category modCategory;
    public static float mRole;
    public static float vRole;
    public TimerUtils timer = new TimerUtils();
    public float dragX;
    public float dragY;

    public void initGui() {
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Gui.isHovered(windowX, windowY, windowX + width, windowY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            if (this.dragX == 0.0f && this.dragY == 0.0f) {
                this.dragX = (float)mouseX - windowX;
                this.dragY = (float)mouseY - windowY;
            } else {
                windowX = (float)mouseX - this.dragX;
                windowY = (float)mouseY - this.dragY;
            }
        } else if (this.dragX != 0.0f || this.dragY != 0.0f) {
            this.dragX = 0.0f;
            this.dragY = 0.0f;
        }
        int dWheel = Mouse.getDWheel();
        if (Gui.isHovered(windowX + 200.0f, windowY + 40.0f, windowX + width, windowY + height, mouseX, mouseY)) {
            if (dWheel < 0 && Math.abs(vRole) + 170.0f < (float)(Client.moduleManager.getModules().size() * 25)) {
                vRole -= 16.0f;
            }
            if (dWheel > 0 && vRole < 0.0f) {
                vRole += 16.0f;
            }
        }
        if (Gui.isHovered(windowX, windowY + 40.0f, windowX + 200.0f, windowY + height - 20.0f, mouseX, mouseY)) {
            if (dWheel < 0 && Math.abs(mRole) + 200.0f < (float)(Client.moduleManager.getModulesForCategory(modCategory).size() * 30)) {
                mRole -= 16.0f;
            }
            if (dWheel > 0 && mRole < 0.0f) {
                mRole += 16.0f;
            }
        }
        HUDUtils.drawRect(windowX, windowY, windowX + width, windowY + height, new Color(0, 0, 0, 10).getRGB());
        HUDUtils.drawRect(windowX, windowY, windowX + width, windowY + height, new Color(0, 0, 0, 100).getRGB());
        HUDUtils.drawRect(windowX, windowY, windowX + width, windowY + 40.0f, new Color(0, 0, 0, 120).getRGB());
        float cx = windowX + 50.0f;
        for (Category mc : Category.values()) {
            HUDUtils.drawImage((int)cx, (int)windowY + 10, 16, 16, new ResourceLocation("Fair/" + mc.name() + ".png"), new Color(255, 255, 255));
            if (Gui.isHovered(cx, windowY + 10.0f, cx + 16.0f, windowY + 26.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0) && this.timer.delay(300.0f)) {
                modCategory = mc;
                mRole = 0.0f;
                vRole = 0.0f;
                this.timer.reset();
            }
            cx += (width - 116.0f) / (float)(Category.values().length - 2);
        }
        HUDUtils.drawRect(windowX + 200.0f, windowY + 50.0f, windowX + 201.0f, windowY + height - 10.0f, new Color(150, 150, 150, 100).getRGB());
        float vY = windowY + 50.0f + vRole;
        if (currentMod != null) {
            for (Setting v : currentMod.getSetting()) {
                if (vY + 30.0f < windowY + 70.0f) {
                    vY += 25.0f;
                }
                if (vY + 30.0f < windowY + 70.0f || vY + 30.0f > windowY + height) continue;
                if (v instanceof EnableSetting) {
                    FontLoaders.F16.drawString(v.getName(), windowX + 210.0f, vY, -1);
                }
                vY += 25.0f;
            }
        }
        float mY = windowY + 50.0f + mRole;
        for (Module m : Client.moduleManager.getModulesForCategory(modCategory)) {
            if (mY + 30.0f < windowY + 70.0f) {
                mY += 30.0f;
            }
            if (mY + 30.0f < windowY + 70.0f || mY + 30.0f > windowY + height) continue;
            if (m.getState()) {
                HUDUtils.drawRect(windowX + 10.0f, mY, windowX + 190.0f, mY + 25.0f, new Color(255, 255, 255, 100).getRGB());
            } else {
                HUDUtils.drawRect(windowX + 10.0f, mY, windowX + 190.0f, mY + 25.0f, new Color(0, 0, 0, 50).getRGB());
            }
            if (Gui.isHovered(windowX + 10.0f, mY, windowX + 190.0f, mY + 25.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0) && this.timer.delay(300.0f)) {
                m.gtoggle();
                this.timer.reset();
            } else if (Gui.isHovered(windowX + 10.0f, mY, windowX + 190.0f, mY + 25.0f, mouseX, mouseY) && Mouse.isButtonDown((int)1) && this.timer.delay(300.0f)) {
                currentMod = m;
                this.timer.reset();
            }
            FontLoaders.F18.drawString(m.getName(), windowX + 15.0f, mY + 5.0f, -1);
            FontLoaders.F14.drawString(m.getDescription(), windowX + 20.0f, mY + 15.0f, new Color(230, 230, 230).getRGB());
            mY += 30.0f;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    static {
        modCategory = Category.Combat;
    }
}

