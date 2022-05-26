/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.misc;

import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAFK
extends Module {
    private TimerUtils timerUtils = new TimerUtils();
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);
    private IntegerSetting Delay = new IntegerSetting("Delay", 10.0, 1.0, 100.0);

    public AntiAFK() {
        super("AntiAFK", 0, Category.Misc, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (this.timerUtils.isDelayComplete(2000.0)) {
            AntiAFK.mc.thePlayer.jump();
            this.timerUtils.reset();
        }
    }
}

