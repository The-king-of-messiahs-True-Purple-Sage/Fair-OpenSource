/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package com.Fair.GhostClient.modules.world;

import com.Fair.GhostClient.Utils.Mappings;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Timer
extends Module {
    private IntegerSetting TimerSpeed = new IntegerSetting("TimerSpeed", 2.0, 0.1, 5.0);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);
    net.minecraft.util.Timer timer = (net.minecraft.util.Timer)ReflectionHelper.getPrivateValue(Minecraft.class, (Object)mc, (String[])new String[]{Mappings.timer});

    public Timer() {
        super("Timer", 0, Category.World, false, "");
        this.getSetting().add(this.TimerSpeed);
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        this.timer.timerSpeed = (float)this.TimerSpeed.getCurrent();
    }

    @Override
    public void disable() {
        this.timer.timerSpeed = 1.0f;
        super.disable();
    }
}

