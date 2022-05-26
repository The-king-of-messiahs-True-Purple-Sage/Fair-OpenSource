/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright
extends Module {
    private float old;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public FullBright() {
        super("FullBright", 0, Category.Render, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        this.old = FullBright.mc.gameSettings.gammaSetting;
        super.enable();
    }

    @Override
    public void disable() {
        super.enable();
        FullBright.mc.gameSettings.gammaSetting = this.old;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            this.disable();
            return;
        }
        if (FullBright.mc.gameSettings.gammaSetting != 10000.0f) {
            FullBright.mc.gameSettings.gammaSetting = 10000.0f;
        }
    }
}

