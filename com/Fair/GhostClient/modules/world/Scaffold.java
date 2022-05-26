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
 *  org.lwjgl.input.Keyboard
 */
package com.Fair.GhostClient.modules.world;

import com.Fair.GhostClient.Utils.ReflectionUtil;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Scaffold
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);
    ArrayList<String> sites = new ArrayList();
    public int godBridgeTimer;

    public Scaffold() {
        super("Scaffold", 0, Category.World, false, "");
        this.getSetting().add(this.KeyBind);
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

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        this.Eagle();
    }

    void Eagle() {
        try {
            if (Scaffold.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                if (!Scaffold.mc.gameSettings.keyBindJump.isPressed()) {
                    BlockPos bp = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
                    if (Scaffold.mc.theWorld.getBlockState(bp).getBlock() == Blocks.air && !Keyboard.isKeyDown((int)17)) {
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

