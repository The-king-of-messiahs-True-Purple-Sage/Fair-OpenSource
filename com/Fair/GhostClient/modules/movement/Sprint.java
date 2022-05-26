/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.movement;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public Sprint() {
        super("Sprint", 0, Category.Movement, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (!Sprint.mc.thePlayer.isCollidedHorizontally && Sprint.mc.thePlayer.field_70701_bs > 0.0f) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}

