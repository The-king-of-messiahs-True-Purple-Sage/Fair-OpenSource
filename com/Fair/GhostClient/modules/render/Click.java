/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.CFontRenderer;
import com.Fair.GhostClient.Utils.FontLoaders;
import com.Fair.GhostClient.config.configs.ClickGuiConfig;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.gui.clickgui.ClickGUI;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import com.Fair.GhostClient.settings.ModeSetting;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.gui.GuiScreen;

public class Click
extends Module {
    public ClickGUI clickgui;
    public static IntegerSetting red = new IntegerSetting("Red", 0.0, 0.0, 255.0);
    public static IntegerSetting green = new IntegerSetting("Green", 255.0, 0.0, 255.0);
    public static IntegerSetting blue = new IntegerSetting("Blue", 148.0, 0.0, 255.0);
    public static EnableSetting useFont = new EnableSetting("UseFont", false);
    private final ModeSetting Mode;
    public static String font;
    private boolean isEmpty;
    public static EnableSetting rainbow;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    @Override
    public void isSilder() {
        CategoryPanel.color = new Color((int)red.getCurrent(), (int)green.getCurrent(), (int)blue.getCurrent()).getRGB();
        CategoryPanel.colors = new Color((int)red.getCurrent(), (int)green.getCurrent(), (int)blue.getCurrent());
        super.isSilder();
    }

    public Click() {
        super("ClickGUI", 54, Category.Render, false, "ClickGUI");
        this.getSetting().add(red);
        this.getSetting().add(green);
        this.getSetting().add(blue);
        this.getSetting().add(useFont);
        File n = new File(Tools.getFontPath());
        File[] fs = n.listFiles();
        ArrayList<String> arrayList = new ArrayList<String>();
        File ConfigDir = new File(Tools.getConfigPath());
        System.out.println("ccccccccccccccccc");
        System.out.println("cccccccccccccccccaaaaa");
        ConfigDir.mkdir();
        File FontDir = new File(Tools.getFontPath());
        FontDir.mkdir();
        try {
            for (File file : fs) {
                if (file.isDirectory() || !Click.getExtension(file.getName()).equalsIgnoreCase("ttf")) continue;
                arrayList.add(file.getName());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList.size() == 0) {
            this.isEmpty = true;
            this.Mode = new ModeSetting("Font", "", Arrays.asList(""), this);
        } else {
            this.Mode = new ModeSetting("Font", (String)arrayList.get(0), arrayList, this);
            font = this.Mode.getCurrent();
        }
        this.getSetting().add(this.Mode);
        this.getSetting().add(this.KeyBind);
    }

    private static String getExtension(String file) {
        String f = file;
        if (f.lastIndexOf(".") != -1 && f.lastIndexOf(".") != 0) {
            return f.substring(f.lastIndexOf(".") + 1);
        }
        return "";
    }

    @Override
    public void setMode() {
        super.setMode();
        if (!this.isEmpty) {
            font = this.Mode.getCurrent();
            System.out.println(font);
            Client.cFontRenderer = FontLoaders.F16 = new CFontRenderer(FontLoaders.getFont(16), true, true);
        }
    }

    @Override
    public void enable() {
        super.enable();
        if (this.clickgui == null) {
            this.clickgui = new ClickGUI();
        }
        if (!this.isEmpty) {
            Client.cFontRenderer = FontLoaders.F16 = new CFontRenderer(FontLoaders.getFont(16), true, true);
        }
        mc.displayGuiScreen((GuiScreen)this.clickgui);
        this.toggle();
        ClickGuiConfig.loadClickGui();
        CategoryPanel.color = new Color((int)red.getCurrent(), (int)green.getCurrent(), (int)blue.getCurrent()).getRGB();
    }

    static {
        rainbow = new EnableSetting("Rainbow", false);
    }
}

