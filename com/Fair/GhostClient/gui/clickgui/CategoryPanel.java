/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.gui.clickgui;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.ClickGUIUtils;
import com.Fair.GhostClient.Utils.FontUtil;
import com.Fair.GhostClient.gui.clickgui.Events;
import com.Fair.GhostClient.gui.clickgui.ModulePanel;
import com.Fair.GhostClient.gui.clickgui.setting.SettingPanel;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.render.Click;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public class CategoryPanel {
    public Category category;
    private boolean hovered;
    private boolean hov;
    private int tempX;
    private int tempY;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean move;
    private Module module;
    public ArrayList<ModulePanel> modulePanels;
    public ArrayList<Events> events;
    private boolean displayModulePanel;
    public static boolean isClick = false;
    public int barHeight;
    private int scroll;
    private String m;
    public static int color = new Color(30, 30, 30).getRGB();
    public static Color colors = new Color(30, 30, 30);
    private float valueRole;
    public static FontRenderer fontRenderer;

    public CategoryPanel(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        FontUtil.setupFontUtils();
        this.barHeight = 23;
        this.modulePanels = new ArrayList();
        this.events = new ArrayList();
        int tY = this.barHeight;
        ArrayList<Module> modules = new ArrayList<Module>();
        modules.addAll(Client.moduleManager.getModulesForCategory(this.category));
        int modulePanelY = this.y + this.height;
        for (Module m : Client.moduleManager.getModulesForCategory(category)) {
            this.events.add(new ModulePanel(m, tY));
            tY += 25;
        }
        for (Module m : modules) {
            System.out.println(m.getName());
            this.modulePanels.add(new ModulePanel(m, tY));
            modulePanelY += 25;
        }
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

    public ArrayList<Events> getEvents() {
        return this.events;
    }

    public void setEvents(ArrayList<Events> events) {
        this.events = events;
    }

    public static int getColor() {
        return color;
    }

    public void drawScreen(int mouseX, int mouseY, float particalTicks) {
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        GL11.glPushMatrix();
        GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        if (this.move) {
            this.x = this.tempX + mouseX;
            this.y = this.tempY + mouseY;
        }
        ClickGUIUtils.drawRect(this.x, this.y, this.width, this.height, color);
        if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawCenteredStringWithShadow(this.category.name(), this.x + this.width / 2, this.y + this.height / 2 - 2, Color.WHITE.getRGB());
        } else {
            FontUtil.drawCenteredStringWithShadow(this.category.name(), this.x + this.width / 2, this.y + this.height / 2 - 4, Color.WHITE.getRGB());
        }
        if (this.displayModulePanel) {
            FontUtil.drawString("-", this.x + this.width - FontUtil.getStringWidth("-") * 2, this.y + this.height / 2 - 4, Color.WHITE.getRGB());
            this.hov = ClickGUIUtils.isHovered(mouseX, mouseY, this.x + this.width - FontUtil.getStringWidth("-") * 2, this.y + this.height / 2 - 2, FontUtil.getStringWidth("-"), FontUtil.getFontHeight());
            int modulePanelY = this.y + this.height;
            int modulePanelY1 = 0;
            for (ModulePanel modulePanel : this.modulePanels) {
                modulePanelY1 += 16;
                if (modulePanel.isSetting()) {
                    modulePanelY1 += modulePanel.getOpY();
                }
                for (SettingPanel it : modulePanel.getSettingPanelList()) {
                    if (!it.isModedr() || !modulePanel.isSetting()) continue;
                    modulePanelY1 += it.getModeY();
                }
            }
            ClickGUIUtils.drawRect(this.x, this.y + 19, this.width, modulePanelY1, new Color(24, 24, 24, 151).getRGB());
            for (ModulePanel modulePanel : this.modulePanels) {
                modulePanel.drawScreen(mouseX, mouseY, particalTicks, this.x + 1, modulePanelY, 96, 15);
                modulePanelY += 16;
                if (modulePanel.isSetting()) {
                    modulePanelY += modulePanel.getOpY();
                }
                for (SettingPanel it : modulePanel.getSettingPanelList()) {
                    if (!it.isModedr() || !modulePanel.isSetting()) continue;
                    modulePanelY += it.getModeY();
                }
            }
        } else {
            FontUtil.drawString("+", this.x + this.width - FontUtil.getStringWidth("+") * 2, this.y + this.height / 2 - 4, Color.WHITE.getRGB());
            this.hov = ClickGUIUtils.isHovered(mouseX, mouseY, this.x + this.width - FontUtil.getStringWidth("+") * 2, this.y + this.height / 2 - 2, FontUtil.getStringWidth("+"), FontUtil.getFontHeight());
        }
    }

    public int getMaxModule() {
        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<Module> modules = Client.moduleManager.getModules();
        modules.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getName()) - mc.fontRendererObj.getStringWidth(o1.getName()));
        return mc.fontRendererObj.getStringWidth(modules.get(0).getName());
    }

    public boolean isDisplayModulePanel() {
        return this.displayModulePanel;
    }

    public void setDisplayModulePanel(boolean displayModulePanel) {
        this.displayModulePanel = displayModulePanel;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.hov && mouseButton == 0) {
            this.displayModulePanel = !this.displayModulePanel;
            boolean bl = this.displayModulePanel;
        }
        if (this.hovered && mouseButton == 0) {
            Client.hovca = this.category.name();
            if (this.category.name().equalsIgnoreCase(Client.hovca)) {
                isClick = true;
                this.move = true;
                this.tempX = this.x - mouseX;
                this.tempY = this.y - mouseY;
            }
        } else if (this.hovered && mouseButton == 1) {
            this.displayModulePanel = !this.displayModulePanel;
        }
        for (ModulePanel modulePanel : this.modulePanels) {
            for (SettingPanel it : modulePanel.getSettingPanelList()) {
                it.mouseClicked1(mouseX, mouseY, mouseButton);
            }
        }
        for (ModulePanel modulePanel : this.modulePanels) {
            modulePanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        isClick = false;
        if (state == 0) {
            this.move = false;
            for (ModulePanel modulePanel : this.modulePanels) {
                modulePanel.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
}

