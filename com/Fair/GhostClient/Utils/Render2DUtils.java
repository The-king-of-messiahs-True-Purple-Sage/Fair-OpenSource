/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 */
package com.Fair.GhostClient.Utils;

import net.minecraft.client.gui.Gui;

public class Render2DUtils {
    public static void drawRect(int x, int y, int width, int height, int color) {
        Gui.drawRect((int)x, (int)y, (int)(x + width), (int)(y + height), (int)color);
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}

