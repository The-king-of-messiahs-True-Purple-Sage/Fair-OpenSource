/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.world;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastPlace
extends Module {
    Field c = null;
    public Minecraft mc = Minecraft.getMinecraft();
    private IntegerSetting Dalay = new IntegerSetting("Dalay", 0.0, 0.0, 4.0);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public FastPlace() {
        super("FastPlace", 0, Category.World, false, "");
        this.getSetting().add(this.Dalay);
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        try {
            this.c = this.mc.getClass().getDeclaredField("field_71467_ac");
        }
        catch (Exception d) {
            try {
                this.c = this.mc.getClass().getDeclaredField("rightClickDelayTimer");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.c != null) {
            this.c.setAccessible(true);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (e.phase == TickEvent.Phase.END && Tools.isPlayerInGame() && this.mc.inGameHasFocus && this.c != null) {
            ItemStack item = this.mc.thePlayer.getHeldItem();
            if (item == null || !(item.getItem() instanceof ItemBlock)) {
                return;
            }
            try {
                int c1 = (int)this.Dalay.getCurrent();
                if (c1 == 0) {
                    this.c.set(this.mc, 0);
                } else {
                    if (c1 == 4) {
                        return;
                    }
                    int d = this.c.getInt(this.mc);
                    if (d == 4) {
                        this.c.set(this.mc, c1);
                    }
                }
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
    }
}

