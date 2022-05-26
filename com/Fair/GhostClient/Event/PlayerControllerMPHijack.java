/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.world.WorldSettings$GameType
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 *  net.minecraftforge.fml.relauncher.ReflectionHelper$UnableToFindMethodException
 */
package com.Fair.GhostClient.Event;

import com.Fair.GhostClient.modules.world.BuildReach;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

class PlayerControllerMPHijack
extends PlayerControllerMP {
    private static Method syncCurrentPlayItemMethod;

    public static Method findMethod(Class clazz, String[] methodNames, Class<?> ... methodTypes) {
        Exception failed = null;
        for (String methodName : methodNames) {
            try {
                Method m = clazz.getDeclaredMethod(methodName, methodTypes);
                m.setAccessible(true);
                return m;
            }
            catch (Exception e) {
                failed = e;
            }
        }
        throw new ReflectionHelper.UnableToFindMethodException(methodNames, failed);
    }

    public static PlayerControllerMPHijack createFromVanilla(PlayerControllerMP playerControllerMP) {
        NetHandlerPlayClient netHandlerPlayClient = (NetHandlerPlayClient)ReflectionHelper.getPrivateValue(PlayerControllerMP.class, (Object)playerControllerMP, (String[])new String[]{"netClientHandler", "field_78774_b"});
        PlayerControllerMPHijack retObj = new PlayerControllerMPHijack(Minecraft.getMinecraft(), netHandlerPlayClient);
        WorldSettings.GameType gameType = (WorldSettings.GameType)ReflectionHelper.getPrivateValue(PlayerControllerMP.class, (Object)playerControllerMP, (String[])new String[]{"currentGameType", "field_78779_k"});
        ReflectionHelper.setPrivateValue(PlayerControllerMP.class, (Object)((Object)retObj), (Object)gameType, (String[])new String[]{"currentGameType", "field_78779_k"});
        syncCurrentPlayItemMethod = PlayerControllerMPHijack.findMethod(PlayerControllerMP.class, new String[]{"syncCurrentPlayItem", "func_78750_j"}, new Class[0]);
        return retObj;
    }

    private PlayerControllerMPHijack(Minecraft mcIn, NetHandlerPlayClient netHandlerPlayClient) {
        super(mcIn, netHandlerPlayClient);
    }

    public float getBlockReachDistance() {
        if (BuildReach.isBuildReach) {
            return BuildReach.BuildDistance;
        }
        return super.getBlockReachDistance();
    }
}

