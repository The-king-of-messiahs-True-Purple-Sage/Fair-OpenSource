/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldSettings$GameType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.misc;

import com.Fair.GhostClient.Utils.ReflectionUtil;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MLG
extends Module {
    private static float dalay = 10.0f;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public MLG() {
        super("MLG", 0, Category.Misc, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onPre(TickEvent.PlayerTickEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (MLG.mc.thePlayer.fallDistance > 4.0f && this.getSlotWaterBucket() != -1 && this.isMLGNeeded()) {
            MLG.mc.thePlayer.rotationPitch = 90.0f;
            this.swapToWaterBucket(this.getSlotWaterBucket());
        }
        if (MLG.mc.thePlayer.fallDistance > 4.0f && this.isMLGNeeded() && !MLG.mc.thePlayer.func_70617_f_() && MLG.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos pos = new BlockPos(MLG.mc.thePlayer.posX, MLG.mc.thePlayer.posY - MLG.getDistanceToFall() - 1.0, MLG.mc.thePlayer.posZ);
            this.placeWater(pos, EnumFacing.UP);
            if (MLG.mc.thePlayer.getHeldItem().getItem() == Items.bucket) {
                Thread thr = new Thread(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep((long)dalay);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ReflectionUtil.rightClickMouse();
                    }
                };
                thr.start();
            }
            MLG.mc.thePlayer.fallDistance = 0.0f;
        }
    }

    public static double getDistanceToFall() {
        double distance = 0.0;
        for (double i = MLG.mc.thePlayer.posY; i > 0.0; i -= 1.0) {
            Block block = MLG.getBlock(new BlockPos(MLG.mc.thePlayer.posX, i, MLG.mc.thePlayer.posZ));
            if (block.getMaterial() != Material.air && block.isBlockNormalCube() && block.isCollidable()) {
                distance = i;
                break;
            }
            if (i < 0.0) break;
        }
        double distancetofall = MLG.mc.thePlayer.posY - distance - 1.0;
        return distancetofall;
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }

    private void swapToWaterBucket(int blockSlot) {
        MLG.mc.thePlayer.inventory.currentItem = blockSlot;
        MLG.mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(blockSlot));
    }

    private int getSlotWaterBucket() {
        for (int i = 0; i < 8; ++i) {
            if (MLG.mc.thePlayer.inventory.mainInventory[i] == null || !MLG.mc.thePlayer.inventory.mainInventory[i].getItem().getUnlocalizedName().contains("bucketWater")) continue;
            return i;
        }
        return -1;
    }

    private void placeWater(BlockPos pos, EnumFacing facing) {
        ItemStack heldItem = MLG.mc.thePlayer.inventory.getCurrentItem();
        MLG.mc.playerController.onPlayerRightClick(MLG.mc.thePlayer, MLG.mc.theWorld, MLG.mc.thePlayer.inventory.getCurrentItem(), pos, facing, new Vec3((double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 1.0, (double)pos.func_177952_p() + 0.5));
        if (heldItem != null) {
            MLG.mc.playerController.sendUseItem((EntityPlayer)MLG.mc.thePlayer, (World)MLG.mc.theWorld, heldItem);
            MLG.mc.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
    }

    private boolean isMLGNeeded() {
        if (MLG.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE || MLG.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR || MLG.mc.thePlayer.capabilities.isFlying || MLG.mc.thePlayer.capabilities.allowFlying) {
            return false;
        }
        for (double y = MLG.mc.getMinecraft().thePlayer.posY; y > 0.0; y -= 1.0) {
            Block block = MLG.getBlock(new BlockPos(MLG.mc.getMinecraft().thePlayer.posX, y, MLG.mc.getMinecraft().thePlayer.posZ));
            if (block.getMaterial() == Material.water) {
                return false;
            }
            if (block.getMaterial() != Material.air) {
                return true;
            }
            if (y < 0.0) break;
        }
        return true;
    }
}

