/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.inventory.GuiContainerCreative
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Utils.TimeUtil;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChestStealer
extends Module {
    private TimerUtils timer = new TimerUtils();
    public int ticks;
    private TimeUtil timerUtil = new TimeUtil();
    public IntegerSetting Delay = new IntegerSetting("Delay", 200.0, 0.0, 1000.0);
    private EnableSetting autoclose = new EnableSetting("Auto close chest", true);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public ChestStealer() {
        super("ChestStealer", 0, Category.Combat, false, "Auto");
        this.getSetting().add(this.Delay);
        this.getSetting().add(this.autoclose);
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        Minecraft mc = ChestStealer.mc;
        if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiContainerCreative)) {
            ContainerChest container = (ContainerChest)mc.thePlayer.openContainer;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null) {
                    if (!this.timer.isDelayComplete(this.Delay.getCurrent() + (double)new Random().nextInt(100))) continue;
                    PlayerControllerMP playerController = mc.playerController;
                    int windowId = container.windowId;
                    int slotId = i;
                    boolean p_78753_3_ = false;
                    boolean p_78753_4_ = true;
                    playerController.windowClick(windowId, slotId, 0, 1, (EntityPlayer)mc.thePlayer);
                    this.timer.reset();
                    continue;
                }
                if (!this.empty((Container)container) || !this.autoclose.getEnable()) continue;
                mc.thePlayer.closeScreen();
            }
            ++this.ticks;
        }
    }

    private boolean isEmpty() {
        if (ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)ChestStealer.mc.thePlayer.openContainer;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack == null || itemStack.getItem() == null) continue;
                return false;
            }
        }
        return true;
    }

    private boolean isContainerEmpty(Container container) {
        int slotAmount;
        boolean temp = true;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 35;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            temp = false;
        }
        return temp;
    }

    public boolean empty(Container container) {
        int slotAmount;
        boolean voll = true;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            voll = false;
        }
        return voll;
    }
}

