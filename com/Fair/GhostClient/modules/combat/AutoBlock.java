/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Utils.CoolDown;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoBlock
extends Module {
    private boolean engaged;
    private CoolDown engagedTime = new CoolDown(0L);
    private IntegerSetting duration = new IntegerSetting("Block duration (MS)", 100.0, 1.0, 500.0);
    private IntegerSetting distance = new IntegerSetting("distance", 4.0, 3.0, 6.0);
    private IntegerSetting chance = new IntegerSetting("chance", 100.0, 1.0, 100.0);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public AutoBlock() {
        super("AutoBlock", 0, Category.Combat, false, "");
        this.getSetting().add(this.duration);
        this.getSetting().add(this.distance);
        this.getSetting().add(this.chance);
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void yes(TickEvent.RenderTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (this.engaged) {
            if ((this.engagedTime.hasTimeElapsed() || !Mouse.isButtonDown((int)0)) && this.duration.getCurrent() <= (double)this.engagedTime.getElapsedTime()) {
                this.engaged = false;
                AutoBlock.release();
            }
            return;
        }
        if (Mouse.isButtonDown((int)0) && AutoBlock.mc.objectMouseOver != null && AutoBlock.mc.objectMouseOver.entityHit instanceof Entity && (double)AutoBlock.mc.thePlayer.getDistanceToEntity(AutoBlock.mc.objectMouseOver.entityHit) >= this.distance.getCurrent() && AutoBlock.mc.objectMouseOver.entityHit instanceof Entity && (double)AutoBlock.mc.thePlayer.getDistanceToEntity(AutoBlock.mc.objectMouseOver.entityHit) <= this.distance.getCurrent() && (this.chance.getCurrent() == 100.0 || Math.random() <= this.chance.getCurrent() / 100.0)) {
            this.engaged = true;
            this.engagedTime.setCooldown((long)this.duration.getCurrent());
            this.engagedTime.start();
            AutoBlock.press();
        }
    }

    private static void release() {
        int key = AutoBlock.mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState((int)key, (boolean)false);
        Tools.setMouseButtonState(1, false);
    }

    private static void press() {
        int key = AutoBlock.mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState((int)key, (boolean)true);
        KeyBinding.onTick((int)key);
        Tools.setMouseButtonState(1, true);
    }
}

