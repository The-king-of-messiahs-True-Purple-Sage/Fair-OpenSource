/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  org.lwjgl.input.Keyboard
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.ColorUtils;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.ModuleManager;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.settings.EnableSetting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Hud
extends Module {
    public ColorUtils c = new ColorUtils();
    private final ArrayList<Tab> tabs;
    private final Minecraft mc = Minecraft.getMinecraft();
    private int selectTab = 0;
    private boolean sub = false;
    private int selectSub = 0;
    private EnableSetting Title = new EnableSetting("Title", true);
    private EnableSetting TabGUI = new EnableSetting("TabGUI", false);
    private EnableSetting ArrayList = new EnableSetting("ArrayList", true);
    private EnableSetting RainBow = new EnableSetting("RainBow", false);

    public Hud() {
        super("Hud", 0, Category.Render, true, "HUD");
        this.tabs = new ArrayList();
        for (Category category : Category.values()) {
            Tab tab = new Tab(category.name().substring(0, 1).toUpperCase(Locale.ROOT) + category.name().substring(1).toLowerCase(Locale.ROOT));
            ModuleManager a = new ModuleManager();
            a.getModuleByType(category).forEach(it -> tab.sub.add(new Tab(it.getName())));
            this.tabs.add(tab);
        }
        this.getSetting().add(this.Title);
        this.getSetting().add(this.ArrayList);
        this.getSetting().add(this.RainBow);
    }

    public int getMaxModule() {
        ArrayList<Module> modules = Client.moduleManager.getModules();
        modules.sort((o1, o2) -> this.mc.fontRendererObj.getStringWidth(o2.getName()) - this.mc.fontRendererObj.getStringWidth(o1.getName()));
        return this.mc.fontRendererObj.getStringWidth(modules.get(0).getName());
    }

    public static int getMaxType() {
        List collect = Arrays.stream(Category.values()).sorted((o1, o2) -> Module.mc.fontRendererObj.getStringWidth(o2.name()) - Module.mc.fontRendererObj.getStringWidth(o1.name())).collect(Collectors.toList());
        return Module.mc.fontRendererObj.getStringWidth(((Category)((Object)collect.get(0))).name());
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (!this.ArrayList.getEnable()) {
            return;
        }
        ScaledResolution s = new ScaledResolution(this.mc);
        int width = new ScaledResolution(this.mc).getScaledWidth();
        int height = new ScaledResolution(this.mc).getScaledHeight();
        int y = 1;
        ArrayList<Module> enabledModules = new ArrayList<Module>();
        int rainbowTickc = 0;
        for (Module m : Client.moduleManager.getModules()) {
            if (++rainbowTickc > 100) {
                rainbowTickc = 0;
            }
            if (!m.state) continue;
            enabledModules.add(m);
        }
        enabledModules.sort(new Comparator<Module>(){

            @Override
            public int compare(Module o1, Module o2) {
                return ((Hud)Hud.this).mc.fontRendererObj.getStringWidth(o2.getName()) - ((Hud)Hud.this).mc.fontRendererObj.getStringWidth(o1.getName());
            }
        });
        Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / 40.0 * 1.4)) % 1.0f, 1.0f, 1.0f));
        for (Module m : enabledModules) {
            int moduleWidth;
            if (m == null || !m.getState()) continue;
            int n = moduleWidth = Click.useFont.getEnable() ? Client.cFontRenderer.getStringWidth(m.name) : this.mc.fontRendererObj.getStringWidth(m.name);
            if (this.RainBow.getEnable()) {
                if (Click.useFont.getEnable()) {
                    Client.cFontRenderer.drawStringWithShadow(m.name, width - moduleWidth - 1, y, rainbow.getRGB());
                } else {
                    this.mc.fontRendererObj.drawStringWithShadow(m.name, (float)(width - moduleWidth - 1), (float)y, rainbow.getRGB());
                }
            } else if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawStringWithShadow(m.name, width - moduleWidth - 1, y, CategoryPanel.getColor());
            } else {
                this.mc.fontRendererObj.drawStringWithShadow(m.name, (float)(width - moduleWidth - 1), (float)y, CategoryPanel.getColor());
            }
            y += this.mc.fontRendererObj.FONT_HEIGHT;
        }
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        Gui.drawRect((int)x, (int)y, (int)(x + width), (int)(y + height), (int)color);
    }

    @SubscribeEvent
    public void Title(RenderGameOverlayEvent.Text event) {
        if (!this.Title.getEnable()) {
            return;
        }
        boolean rainbowTickc = false;
        Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / 40.0 * 1.4)) % 1.0f, 1.0f, 1.0f));
        if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiMainMenu)) {
            return;
        }
        if (this.RainBow.getEnable()) {
            if (Click.useFont.getEnable()) {
                Client.cFontRenderer.drawStringWithShadow("F", 2.0, 2.0, rainbow.getRGB());
                Client.cFontRenderer.drawStringWithShadow("airClient", Client.cFontRenderer.getStringWidth("F") + 2, 2.0, -1);
            } else {
                this.mc.fontRendererObj.drawStringWithShadow("F", 2.0f, 2.0f, rainbow.getRGB());
                this.mc.fontRendererObj.drawStringWithShadow("airClient", (float)(this.mc.fontRendererObj.getStringWidth("F") + 2), 2.0f, -1);
            }
        } else if (Click.useFont.getEnable()) {
            Client.cFontRenderer.drawStringWithShadow("F", 2.0, 2.0, CategoryPanel.getColor());
            Client.cFontRenderer.drawStringWithShadow("airClient - Cracked By \u7d2b\u5723\u771f\u5c0a", Client.cFontRenderer.getStringWidth("F") + 2, 2.0, -1);
        } else {
            this.mc.fontRendererObj.drawStringWithShadow("F", 2.0f, 2.0f, CategoryPanel.color);
            this.mc.fontRendererObj.drawStringWithShadow("airClient - Cracked By \u7d2b\u5723\u771f\u5c0a", (float)(this.mc.fontRendererObj.getStringWidth("F") + 2), 2.0f, -1);
        }
    }

    @SubscribeEvent
    public void Tabui(RenderGameOverlayEvent.Text event) {
        try {
            if (!this.TabGUI.getEnable()) {
                return;
            }
            if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiMainMenu)) {
                return;
            }
            int typeY = 15;
            int indexY = 0;
            int tabX = 5;
            for (Tab tab : this.tabs) {
                this.drawRect(tabX - 1, typeY, Hud.getMaxType() + 1, this.mc.fontRendererObj.FONT_HEIGHT, new Color(20, 100, 190, 150).getRGB());
                this.mc.fontRendererObj.drawString(tab.name, tabX, typeY, 0xFFFFFF);
                if (indexY == this.selectTab) {
                    this.drawRect(tabX, typeY, Hud.getMaxType(), this.mc.fontRendererObj.FONT_HEIGHT, new Color(25, 132, 250, 150).getRGB());
                    if (this.sub) {
                        int moduleY = typeY;
                        int moduleIndex = 0;
                        for (Module m : Client.moduleManager.getModuleByType(Category.values()[this.selectTab])) {
                            this.drawRect(tabX, typeY, Hud.getMaxType() + 1, this.mc.fontRendererObj.FONT_HEIGHT, new Color(20, 100, 190, 150).getRGB());
                            this.drawRect(tabX + Hud.getMaxType(), moduleY, this.getMaxModule() + 1, this.mc.fontRendererObj.FONT_HEIGHT, new Color(69, 129, 255, 226).getRGB());
                            this.mc.fontRendererObj.drawString(m.name, tabX + Hud.getMaxType() + 1, moduleY, 0xFFFFFF);
                            Hud hud = this;
                            if (Client.moduleManager.getModuleByType(hud.category.values()[this.selectTab]).get(moduleIndex).getState()) {
                                this.drawRect(tabX + Hud.getMaxType(), moduleY, this.getMaxModule() + 1, this.mc.fontRendererObj.FONT_HEIGHT, new Color(0, 86, 255, 225).getRGB());
                                this.mc.fontRendererObj.drawString(m.name, tabX + Hud.getMaxType() + 1, moduleY, 0xFFFFFF);
                            }
                            if (moduleIndex == this.selectSub) {
                                this.drawRect(tabX + Hud.getMaxType(), moduleY, this.getMaxModule() + 1, this.mc.fontRendererObj.FONT_HEIGHT, new Color(51, 99, 194, 213).getRGB());
                                this.mc.fontRendererObj.drawString(m.name, tabX + Hud.getMaxType() + 2, moduleY, 0xFFFFFF);
                            }
                            ++moduleIndex;
                            moduleY += this.mc.fontRendererObj.FONT_HEIGHT;
                        }
                    }
                }
                ++indexY;
                typeY += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void up() {
        if (!this.sub) {
            if (this.selectTab > 0) {
                --this.selectTab;
                System.out.println("Tap" + this.selectTab);
            } else {
                this.selectTab = Category.values().length - 1;
                System.out.println("Tap" + this.selectTab);
            }
        } else if (this.selectSub > 0) {
            --this.selectSub;
            System.out.println("Sub" + this.selectSub);
        } else {
            this.selectSub = Client.moduleManager.getModulesForCategory(Category.values()[this.selectTab]).size() - 1;
            System.out.println("Sub" + this.selectSub);
        }
    }

    public void down() {
        if (!this.sub) {
            if (this.selectTab < Category.values().length - 1) {
                ++this.selectTab;
                System.out.println("Tap" + this.selectTab);
            } else {
                this.selectTab = 0;
                System.out.println("Tap" + this.selectTab);
            }
        } else if (this.selectSub < Client.moduleManager.getModulesForCategory(Category.values()[this.selectTab]).size() - 1) {
            ++this.selectSub;
            System.out.println("Sub" + this.selectSub);
        } else {
            this.selectSub = 0;
            System.out.println("Sub" + this.selectSub);
        }
    }

    public void left() {
        this.sub = false;
    }

    public void right() {
        this.sub = true;
        this.selectSub = 0;
    }

    public void enter() {
        if (this.sub) {
            ModuleManager moduleManager = new ModuleManager();
            moduleManager.getModuleByType(Category.values()[this.selectTab]).get(this.selectSub).gtoggle();
        }
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown((int)200)) {
            this.up();
        }
        if (Keyboard.isKeyDown((int)208)) {
            this.down();
        }
        if (Keyboard.isKeyDown((int)203)) {
            this.left();
        }
        if (Keyboard.isKeyDown((int)205)) {
            this.right();
        }
        if (Keyboard.isKeyDown((int)28)) {
            this.enter();
        }
    }

    private static class Tab {
        private final String name;
        private final ArrayList<Tab> sub = new ArrayList();

        private Tab(String name) {
            this.name = name;
        }
    }
}

