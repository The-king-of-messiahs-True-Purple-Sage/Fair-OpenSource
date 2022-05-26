/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.Fair.GhostClient.modules.misc;

import com.Fair.GhostClient.Utils.ReflectionUtil;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SafeWalk
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public SafeWalk() {
        super("SafeWalk", 0, Category.Misc, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        this.Eagle();
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        try {
            ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, false);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.disable();
    }

    void Eagle() {
        try {
            if (SafeWalk.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                if (!SafeWalk.mc.gameSettings.keyBindJump.isPressed()) {
                    BlockPos bp = new BlockPos(SafeWalk.mc.thePlayer.posX, SafeWalk.mc.thePlayer.posY - 1.0, SafeWalk.mc.thePlayer.posZ);
                    if (SafeWalk.mc.theWorld.getBlockState(bp).getBlock() == Blocks.air) {
                        ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, true);
                    } else {
                        ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, false);
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

