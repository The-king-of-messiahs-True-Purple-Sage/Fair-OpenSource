/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$MouseInputEvent
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.Event;

import com.Fair.GhostClient.Event.PlayerControllerMPHijack;
import com.Fair.GhostClient.modules.world.BuildReach;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public enum TestEvent {
    INSTANCE;

    private EntityJoinWorldEvent entityJoinWorldEvent;

    @SubscribeEvent
    public void worldLoadEvent(WorldEvent.Load event) {
        if (event.world instanceof WorldClient) {
            Minecraft mc = Minecraft.getMinecraft();
            PlayerControllerMP playerControllerMP = mc.playerController;
            if (!(playerControllerMP instanceof PlayerControllerMPHijack)) {
                mc.playerController = PlayerControllerMPHijack.createFromVanilla(playerControllerMP);
            }
        }
    }

    @SubscribeEvent
    public void mouseInput(InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButton() == 0 && BuildReach.isBuildReach) {
            ((EntityPlayerMP)this.entityJoinWorldEvent.entity).theItemInWorldManager.setBlockReachDistance((double)BuildReach.BuildDistance);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            this.entityJoinWorldEvent = event;
        }
    }
}

