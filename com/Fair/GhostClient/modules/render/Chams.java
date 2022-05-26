/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderPlayerEvent$Post
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.awt.Color;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public Chams() {
        super("Chams", 0, Category.Render, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre e) {
        if (e.entity != Chams.mc.thePlayer) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
        }
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Post e) {
        if (e.entity != Chams.mc.thePlayer) {
            GL11.glDisable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
        }
    }

    public static Color rainbow(int n) {
        return Color.getHSBColor((float)(Math.ceil((double)(System.currentTimeMillis() + (long)n) / 20.0) % 360.0 / 360.0), 0.8f, 1.0f).brighter();
    }

    public void glColor(float n, int n2, int n3, int n4) {
        GL11.glColor4f((float)(0.003921569f * (float)n2), (float)(0.003921569f * (float)n3), (float)(0.003921569f * (float)n4), (float)n);
    }
}

