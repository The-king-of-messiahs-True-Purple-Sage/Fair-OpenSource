/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoBob
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public NoBob() {
        super("NoBob", 0, Category.Render, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        NoBob.mc.thePlayer.distanceWalkedModified = 0.0f;
    }
}

