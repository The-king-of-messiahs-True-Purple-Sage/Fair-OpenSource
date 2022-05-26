/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.gui.clickgui.Panel;

import java.awt.Color;

public class Panel {
    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;

    public void drawScreen(int mouseX, int mouseY, float particalTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public int getRGBColor() {
        return this.color.getRGB();
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

