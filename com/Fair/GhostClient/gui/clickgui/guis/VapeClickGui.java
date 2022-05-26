/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.gui.clickgui.guis;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontLoaders;
import com.Fair.GhostClient.Utils.HUDUtils;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.gui.clickgui.guis.ClickType;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class VapeClickGui
extends GuiScreen {
    private boolean close = false;
    private boolean closed;
    private float dragX;
    private float dragY;
    private boolean drag = false;
    private int valuemodx = 0;
    private static float modsRole;
    private static float modsRoleNow;
    private static float valueRoleNow;
    private static float valueRole;
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;
    static float windowX;
    static float windowY;
    static float width;
    static float height;
    static ClickType selectType;
    static Category modCategory;
    static Module selectMod;
    float[] typeXAnim = new float[]{windowX + 10.0f, windowX + 10.0f, windowX + 10.0f, windowX + 10.0f};
    float hy = windowY + 40.0f;
    TimerUtils valuetimer = new TimerUtils();

    public void initGui() {
        super.initGui();
        this.percent = 1.33f;
        this.lastPercent = 1.0f;
        this.percent2 = 1.33f;
        this.lastPercent2 = 1.0f;
        this.outro = 1.0f;
        this.lastOutro = 1.0f;
    }

    public float smoothTrans(double current, double last) {
        return (float)(current + (last - current) / 6.0);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sResolution = new ScaledResolution(this.mc);
        ScaledResolution sr = new ScaledResolution(this.mc);
        float outro = this.smoothTrans(this.outro, this.lastOutro);
        if (this.mc.currentScreen == null) {
            GlStateManager.translate((float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), (float)0.0f);
            GlStateManager.scale((float)outro, (float)outro, (float)0.0f);
            GlStateManager.translate((float)(-sr.getScaledWidth() / 2), (float)(-sr.getScaledHeight() / 2), (float)0.0f);
        }
        this.percent = this.smoothTrans(this.percent, this.lastPercent);
        this.percent2 = this.smoothTrans(this.percent2, this.lastPercent2);
        if ((double)this.percent > 0.98) {
            GlStateManager.translate((float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), (float)0.0f);
            GlStateManager.scale((float)this.percent, (float)this.percent, (float)0.0f);
            GlStateManager.translate((float)(-sr.getScaledWidth() / 2), (float)(-sr.getScaledHeight() / 2), (float)0.0f);
        } else if (this.percent2 <= 1.0f) {
            GlStateManager.translate((float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), (float)0.0f);
            GlStateManager.scale((float)this.percent2, (float)this.percent2, (float)0.0f);
            GlStateManager.translate((float)(-sr.getScaledWidth() / 2), (float)(-sr.getScaledHeight() / 2), (float)0.0f);
        }
        if ((double)this.percent <= 1.5 && this.close) {
            this.percent = this.smoothTrans(this.percent, 2.0);
            this.percent2 = this.smoothTrans(this.percent2, 2.0);
        }
        if ((double)this.percent >= 1.4 && this.close) {
            this.percent = 1.5f;
            this.closed = true;
            this.mc.currentScreen = null;
        }
        if (VapeClickGui.isHovered(windowX, windowY, windowX + width, windowY + 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            if (this.dragX == 0.0f && this.dragY == 0.0f) {
                this.dragX = (float)mouseX - windowX;
                this.dragY = (float)mouseY - windowY;
            } else {
                windowX = (float)mouseX - this.dragX;
                windowY = (float)mouseY - this.dragY;
            }
            this.drag = true;
        } else if (this.dragX != 0.0f || this.dragY != 0.0f) {
            this.dragX = 0.0f;
            this.dragY = 0.0f;
        }
        HUDUtils.drawRect(windowX, windowY, windowX + width, windowY + height, new Color(21, 22, 25).getRGB());
        if (selectMod == null) {
            // empty if block
        }
        if (selectType == ClickType.Home) {
            GL11.glEnable((int)3089);
            GL11.glScissor((int)0, (int)(2 * (int)(sr.getScaledHeight_double() - (double)(windowY + height)) + 40), (int)((int)(sr.getScaledWidth_double() * 2.0)), (int)((int)(height * 2.0f) - 160));
            if (selectMod == null) {
                float cateY = windowY + 65.0f;
                for (Category category : Category.values()) {
                    if (category == modCategory) {
                        FontLoaders.F18.drawString(category.name(), windowX + 20.0f, cateY, -1);
                        if (VapeClickGui.isHovered(windowX, windowY, windowX + width, windowY + 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                            this.hy = cateY;
                        } else if (this.hy != cateY) {
                            this.hy += (cateY - this.hy) / 20.0f;
                        }
                    } else {
                        FontLoaders.F18.drawString(category.name(), windowX + 20.0f, cateY, new Color(108, 109, 113).getRGB());
                    }
                    cateY += 25.0f;
                }
            }
            if (selectMod != null) {
                if (this.valuemodx > -80) {
                    this.valuemodx -= 5;
                }
            } else if (this.valuemodx < 0) {
                this.valuemodx += 5;
            }
            if (selectMod != null) {
                if (VapeClickGui.isHovered(windowX + 435.0f + (float)this.valuemodx, windowY + 65.0f, windowX + 435.0f + (float)this.valuemodx + 16.0f, windowY + 65.0f + 16.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                    selectMod = null;
                    this.valuetimer.reset();
                }
                int dWheel = Mouse.getDWheel();
                if (VapeClickGui.isHovered(windowX + 430.0f + (float)this.valuemodx, windowY + 60.0f, windowX + width, windowY + height - 20.0f, mouseX, mouseY)) {
                    if (dWheel < 0 && Math.abs(valueRole) + 170.0f < (float)(Client.moduleManager.getModules().size() * 25)) {
                        valueRole -= 32.0f;
                    }
                    if (dWheel > 0 && valueRole < 0.0f) {
                        valueRole += 32.0f;
                    }
                }
                if (valueRoleNow != valueRole) {
                    valueRoleNow += (valueRole - valueRoleNow) / 20.0f;
                    valueRoleNow = (int)valueRoleNow;
                }
                float valuey = windowY + 100.0f + valueRoleNow;
                if (selectMod == null) {
                    return;
                }
                float modY = windowY + 70.0f + modsRoleNow;
                for (Module module : Client.moduleManager.getModules()) {
                    if (ClickGUIUtils.isHovered((int)windowX + 100 + this.valuemodx, (int)modY - 10, (int)windowX + 425 + this.valuemodx, (int)modY + 25, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                        if (this.valuetimer.delay(300.0f) && modY + 40.0f > windowY + 70.0f && modY < windowY + height) {
                            module.gtoggle();
                            this.valuetimer.reset();
                        }
                    } else if (VapeClickGui.isHovered(windowX + 100.0f + (float)this.valuemodx, modY - 10.0f, windowX + 425.0f + (float)this.valuemodx, modY + 25.0f, mouseX, mouseY) && Mouse.isButtonDown((int)1) && this.valuetimer.delay(300.0f)) {
                        if (selectMod != module) {
                            valueRole = 0.0f;
                            selectMod = module;
                        } else if (selectMod == module) {
                            selectMod = null;
                        }
                        this.valuetimer.reset();
                    }
                    FontLoaders.F20.drawString(".", windowX + 416.0f + (float)this.valuemodx, modY - 5.0f, new Color(66, 64, 70).getRGB());
                    FontLoaders.F20.drawString(".", windowX + 416.0f + (float)this.valuemodx, modY - 1.0f, new Color(66, 64, 70).getRGB());
                    FontLoaders.F20.drawString(".", windowX + 416.0f + (float)this.valuemodx, modY + 3.0f, new Color(66, 64, 70).getRGB());
                    if (VapeClickGui.isHovered(windowX + 100.0f + (float)this.valuemodx, modY - 10.0f, windowX + 425.0f + (float)this.valuemodx, modY + 25.0f, mouseX, mouseY)) {
                        FontLoaders.F16.drawString(module.getDescription() + ".", windowX + 225.0f + (float)this.valuemodx, modY + 5.0f, new Color(94, 95, 98).getRGB());
                    } else {
                        FontLoaders.F16.drawString(module.getDescription() + ".", windowX + 220.0f + (float)this.valuemodx, modY + 5.0f, new Color(94, 95, 98).getRGB());
                    }
                    if (module.getState()) {
                        FontLoaders.F18.drawString(module.getName(), windowX + 140.0f + (float)this.valuemodx, modY + 5.0f, new Color(220, 220, 220).getRGB());
                    } else {
                        FontLoaders.F18.drawString(module.getName(), windowX + 140.0f + (float)this.valuemodx, modY + 5.0f, new Color(108, 109, 113).getRGB());
                    }
                    modY += 40.0f;
                }
                int dWheel2 = Mouse.getDWheel();
                if (VapeClickGui.isHovered(windowX + 100.0f + (float)this.valuemodx, windowY + 60.0f, windowX + 425.0f + (float)this.valuemodx, windowY + height, mouseX, mouseY)) {
                    if (dWheel2 < 0 && Math.abs(modsRole) + 220.0f < (float)(Client.moduleManager.getModulesForCategory(modCategory).size() * 40)) {
                        modsRole -= 32.0f;
                    }
                    if (dWheel2 > 0 && modsRole < 0.0f) {
                        modsRole += 32.0f;
                    }
                }
                if (modsRoleNow != modsRole) {
                    modsRoleNow += (modsRole - modsRoleNow) / 20.0f;
                    modsRoleNow = (int)modsRoleNow;
                }
                GL11.glDisable((int)3089);
            }
            int dWheel2 = Mouse.getDWheel();
            if (VapeClickGui.isHovered(windowX + 100.0f + (float)this.valuemodx, windowY + 60.0f, windowX + 425.0f + (float)this.valuemodx, windowY + height, mouseX, mouseY)) {
                if (dWheel2 < 0 && Math.abs(modsRole) + 220.0f < (float)(Client.moduleManager.getModulesForCategory(modCategory).size() * 40)) {
                    modsRole -= 16.0f;
                }
                if (dWheel2 > 0 && modsRole < 0.0f) {
                    modsRole += 16.0f;
                }
            }
            if (modsRoleNow != modsRole) {
                modsRoleNow += (modsRole - modsRoleNow) / 20.0f;
                modsRoleNow = (int)modsRoleNow;
            }
        }
    }

    public int findArray(float[] a, float b) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] != b) continue;
            return i;
        }
        return 0;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float typeX = windowX + 20.0f;
        for (ClickType e : ClickType.values()) {
            if (e != ClickType.Settings) {
                if (e == selectType) {
                    if (VapeClickGui.isHovered(typeX, windowY + 10.0f, typeX + 16.0f + (float)FontLoaders.F18.getStringWidth(e.name() + " "), windowY + 10.0f + 16.0f, mouseX, mouseY)) {
                        selectType = e;
                    }
                    typeX += (float)(32 + FontLoaders.F18.getStringWidth(e.name() + " "));
                    continue;
                }
                if (VapeClickGui.isHovered(typeX, windowY + 10.0f, typeX + 16.0f, windowY + 10.0f + 16.0f, mouseX, mouseY)) {
                    selectType = e;
                }
                typeX += 32.0f;
                continue;
            }
            if (!VapeClickGui.isHovered(windowX + width - 32.0f, windowY + 10.0f, windowX + width, windowY + 10.0f + 16.0f, mouseX, mouseY)) continue;
            selectType = e;
        }
        if (selectType == ClickType.Home) {
            float cateY = windowY + 65.0f;
            for (Category m : Category.values()) {
                if (VapeClickGui.isHovered(windowX, cateY - 8.0f, windowX + 50.0f, cateY + (float)FontLoaders.F18.getStringHeight("") + 8.0f, mouseX, mouseY)) {
                    if (modCategory != m) {
                        modsRole = 0.0f;
                    }
                    modCategory = m;
                    for (Module module : Client.moduleManager.getModules()) {
                    }
                }
                cateY += 25.0f;
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (!this.closed && keyCode == 1) {
            this.close = true;
            this.mc.mouseHelper.grabMouseCursor();
            this.mc.inGameHasFocus = true;
            return;
        }
        if (this.close) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public void onGuiClosed() {
    }

    static {
        windowX = 200.0f;
        windowY = 200.0f;
        width = 500.0f;
        height = 310.0f;
        selectType = ClickType.Home;
        modCategory = Category.Combat;
    }
}

