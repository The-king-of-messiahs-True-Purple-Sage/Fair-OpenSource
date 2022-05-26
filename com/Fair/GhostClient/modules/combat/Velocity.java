/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity
extends Module {
    private IntegerSetting Horizontal = new IntegerSetting("Horizontal", 100.0, 0.0, 100.0);
    private IntegerSetting Vertical = new IntegerSetting("Vertical", 100.0, 0.0, 100.0);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public Velocity() {
        super("Velocity", 0, Category.Combat, false, "");
        this.getSetting().add(this.Horizontal);
        this.getSetting().add(this.Vertical);
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent ev) {
        if (Tools.isPlayerInGame() && Velocity.mc.thePlayer.field_70738_aO > 0 && Velocity.mc.thePlayer.field_70737_aN == Velocity.mc.thePlayer.field_70738_aO) {
            if (this.Horizontal.getCurrent() != 100.0) {
                Velocity.mc.thePlayer.motionX *= this.Horizontal.getCurrent() / 100.0;
                Velocity.mc.thePlayer.motionZ *= this.Horizontal.getCurrent() / 100.0;
            }
            if (this.Vertical.getCurrent() != 100.0) {
                Velocity.mc.thePlayer.motionY *= this.Vertical.getCurrent() / 100.0;
            }
        }
    }
}

