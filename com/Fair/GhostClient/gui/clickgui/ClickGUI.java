/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.gui.clickgui;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.ReflectionUtil;
import com.Fair.GhostClient.config.configs.ClickGuiConfig;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.gui.clickgui.Events;
import com.Fair.GhostClient.gui.clickgui.Panel.Panel;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.render.Click;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ClickGUI
extends GuiScreen {
    public boolean dragging;
    public boolean extended;
    private Dimension dimension;
    public ClickGUI clickgui;
    public boolean visible;
    private int scroll;
    public static ArrayList<CategoryPanel> categoryPanels;
    public static ArrayList<Panel> panels;

    public ClickGUI() {
        int categoryPanelY = 5;
        categoryPanels = new ArrayList();
        panels = new ArrayList();
        for (Category c : Category.values()) {
            panels.add(new Panel());
            categoryPanels.add(new CategoryPanel(c, 5, categoryPanelY, 98, 19));
            categoryPanelY += 20;
        }
    }

    public static ArrayList<CategoryPanel> getCategoryPanels() {
        return categoryPanels;
    }

    public static void setCategoryPanels(ArrayList<CategoryPanel> categoryPanels) {
        ClickGUI.categoryPanels = categoryPanels;
    }

    public void keyTyped(char typedChar, int keyCode) {
        for (CategoryPanel categoryPanel : categoryPanels) {
            if (!categoryPanel.isDisplayModulePanel() || keyCode == 42) continue;
            for (Events events : categoryPanel.getEvents()) {
                events.keyTyped(typedChar, keyCode);
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    public void onGuiClosed() {
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            try {
                ReflectionUtil.theShaderGroup.set(Minecraft.getMinecraft().entityRenderer, null);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ClickGuiConfig.saveClickGui();
    }

    public static int getR() {
        return (int)Click.red.getCurrent();
    }

    public static int getG() {
        return (int)Click.green.getCurrent();
    }

    public static int getB() {
        return (int)Click.blue.getCurrent();
    }

    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.translate((float)0.0f, (float)this.scroll, (float)0.0f);
        p_drawScreen_2_ -= this.scroll;
        ClickGUIUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight() - this.scroll, new Color(0, 0, 0, 129).getRGB());
        if (Client.hovca != "") {
            // empty if block
        }
        for (CategoryPanel categoryPanel : categoryPanels) {
            if (categoryPanel.category.name().equalsIgnoreCase(Client.hovca)) continue;
            categoryPanel.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        }
        for (CategoryPanel categoryPanel : categoryPanels) {
            if (!categoryPanel.category.name().equalsIgnoreCase(Client.hovca)) continue;
            categoryPanel.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
            break;
        }
        if (Mouse.hasWheel() && Keyboard.isKeyDown((int)42)) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.scroll -= 15;
            } else if (wheel > 0) {
                this.scroll += 15;
                if (this.scroll > 0) {
                    this.scroll = 0;
                }
            }
        }
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    protected void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_) throws IOException {
        p_mouseClicked_2_ -= this.scroll;
        for (CategoryPanel categoryPanel : categoryPanels) {
            categoryPanel.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
        }
        super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
    }

    protected void mouseReleased(int p_mouseReleased_1_, int p_mouseReleased_2_, int p_mouseReleased_3_) {
        p_mouseReleased_2_ -= this.scroll;
        for (CategoryPanel categoryPanel : categoryPanels) {
            categoryPanel.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_, p_mouseReleased_3_);
        }
        super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_, p_mouseReleased_3_);
    }
}

