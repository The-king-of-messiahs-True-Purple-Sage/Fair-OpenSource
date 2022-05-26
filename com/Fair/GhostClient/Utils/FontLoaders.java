/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package com.Fair.GhostClient.Utils;

import com.Fair.GhostClient.Utils.CFontRenderer;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.modules.render.Click;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontLoaders {
    public static final CFontRenderer FLUXICON16 = new CFontRenderer(FontLoaders.FLUX(16), true, true);
    public static CFontRenderer F6 = new CFontRenderer(FontLoaders.getFont(6), true, true);
    public static CFontRenderer F14 = new CFontRenderer(FontLoaders.getFont(14), true, true);
    public static CFontRenderer F16;
    public static CFontRenderer F18;
    public static CFontRenderer F20;
    public static CFontRenderer F22;
    public static CFontRenderer F23;
    public static CFontRenderer F24;
    public static CFontRenderer F30;
    public static CFontRenderer F40;

    public static Font getLocalFont(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/Font.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getFont(int size) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(Tools.getFontPath(), Click.font));
            System.out.println(Click.font);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            ((InputStream)inputStream).close();
            return awtClientFont;
        }
        catch (Exception ex) {
            Font font = new Font("default", 0, size);
            return font;
        }
    }

    private static Font FLUX(int size) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(Tools.getConfigPath(), "ali.ttf"));
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            ((InputStream)inputStream).close();
            return awtClientFont;
        }
        catch (Exception ex) {
            Font font = new Font("default", 0, size);
            return font;
        }
    }

    static {
        F18 = new CFontRenderer(FontLoaders.getFont(18), true, true);
        F20 = new CFontRenderer(FontLoaders.getFont(20), true, true);
        F22 = new CFontRenderer(FontLoaders.getFont(22), true, true);
        F23 = new CFontRenderer(FontLoaders.getFont(23), true, true);
        F24 = new CFontRenderer(FontLoaders.getFont(24), true, true);
        F30 = new CFontRenderer(FontLoaders.getFont(30), true, true);
        F40 = new CFontRenderer(FontLoaders.getFont(40), true, true);
    }
}

