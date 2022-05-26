/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.Fair.GhostClient.modules.movement;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeepSprint
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public KeepSprint() {
        super("KeepSprint", 0, Category.Movement, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        if (!KeepSprint.mc.thePlayer.isSprinting()) {
            KeepSprint.mc.thePlayer.setSprinting(true);
        }
    }
}

