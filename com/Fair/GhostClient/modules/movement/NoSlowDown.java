/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.Fair.GhostClient.modules.movement;

import com.Fair.GhostClient.Event.NoSlowDownUtil;
import com.Fair.GhostClient.Utils.Wrapper;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import com.Fair.GhostClient.settings.ModeSetting;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoSlowDown
extends Module {
    MovementInput origmi;
    private final ModeSetting Mode = new ModeSetting("Mode", "Vanilla", Arrays.asList("Vanilla", "NCP", "AAC5", "AAC", "Custom"), this);
    private IntegerSetting percent = new IntegerSetting("percent", 0.0, 0.0, 100.0);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public NoSlowDown() {
        super("NoSlowDown", 0, Category.Movement, false, "");
        this.getSetting().add(this.Mode);
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        this.origmi = NoSlowDown.mc.thePlayer.movementInput;
        if (!(NoSlowDown.mc.thePlayer.movementInput instanceof NoSlowDownUtil)) {
            NoSlowDown.mc.thePlayer.movementInput = new NoSlowDownUtil(NoSlowDown.mc.gameSettings);
        }
    }

    @Override
    public void disable() {
        NoSlowDown.mc.thePlayer.movementInput = this.origmi;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        double z;
        double y;
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        ItemStack h = NoSlowDown.mc.thePlayer.getHeldItem();
        if (!(NoSlowDown.mc.thePlayer.movementInput instanceof NoSlowDownUtil)) {
            this.origmi = NoSlowDown.mc.thePlayer.movementInput;
            NoSlowDown.mc.thePlayer.movementInput = new NoSlowDownUtil(NoSlowDown.mc.gameSettings);
        }
        if (this.Mode.getCurrent() == "AAC5" && Tools.isBlocking()) {
            mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, NoSlowDown.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        }
        if (NoSlowDown.mc.thePlayer.onGround && !NoSlowDown.mc.gameSettings.keyBindJump.isKeyDown() || NoSlowDown.mc.gameSettings.keyBindSneak.isKeyDown() && NoSlowDown.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            NoSlowDownUtil move = (NoSlowDownUtil)NoSlowDown.mc.thePlayer.movementInput;
            move.setNSD(true);
        }
        if (NoSlowDown.mc.thePlayer.isUsingItem() && Tools.isMoving() && Tools.isOnGround(0.42) && (this.Mode.getCurrent() == "AAC" || this.Mode.getCurrent() == "NCP")) {
            if (this.Mode.getCurrent() == "AAC") {
                Wrapper.getTimer((Minecraft)NoSlowDown.mc).timerSpeed = 0.7f;
            }
            double x = NoSlowDown.mc.thePlayer.posX;
            y = NoSlowDown.mc.thePlayer.posY;
            z = NoSlowDown.mc.thePlayer.posZ;
            NoSlowDown.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (!NoSlowDown.mc.thePlayer.isUsingItem() && this.Mode.getCurrent() == "AAC") {
            Wrapper.getTimer((Minecraft)NoSlowDown.mc).timerSpeed = 1.0f;
        }
        if (this.Mode.getCurrent() == "Custom") {
            try {
                if (NoSlowDown.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) {
                    NoSlowDown.mc.thePlayer.motionX *= 0.5;
                    NoSlowDown.mc.thePlayer.motionZ *= 0.5;
                }
                if (NoSlowDown.mc.thePlayer.isUsingItem()) {
                    NoSlowDown.mc.thePlayer.motionX *= this.percent.getCurrent() / 100.0;
                    NoSlowDown.mc.thePlayer.motionZ *= this.percent.getCurrent() / 100.0;
                }
            }
            catch (NullPointerException x) {
                // empty catch block
            }
        }
        if (NoSlowDown.mc.thePlayer.isUsingItem() && Tools.isMoving() && Tools.isOnGround(0.42) && (this.Mode.getCurrent() == "AAC" || this.Mode.getCurrent() == "NCP")) {
            double x = NoSlowDown.mc.thePlayer.posX;
            y = NoSlowDown.mc.thePlayer.posY;
            z = NoSlowDown.mc.thePlayer.posZ;
            NoSlowDown.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(NoSlowDown.mc.thePlayer.inventory.getCurrentItem()));
        }
    }
}

