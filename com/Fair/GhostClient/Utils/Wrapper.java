/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.util.Timer
 */
package com.Fair.GhostClient.Utils;

import com.Fair.GhostClient.Utils.ReflectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;

public class Wrapper {
    public static Timer getTimer(Minecraft mc) {
        return (Timer)ReflectUtil.getField("timer", "field_71428_T", mc);
    }

    public static double getRenderPosX() {
        return (Double)ReflectUtil.getField("renderPosX", "field_78725_b", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() {
        return (Double)ReflectUtil.getField("renderPosY", "field_78726_c", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() {
        return (Double)ReflectUtil.getField("renderPosZ", "field_78723_d", Minecraft.getMinecraft().getRenderManager());
    }

    public static EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static void orientCamera(float pass) {
        ReflectUtil.invoke(Minecraft.getMinecraft().entityRenderer, "orientCamera", "func_78467_g", new Class[]{Float.TYPE}, new Object[]{Float.valueOf(pass)});
    }

    public static void clickMouse() {
        ReflectUtil.invoke(Minecraft.getMinecraft(), "clickMouse", "func_147116_af", new Class[0], new Object[0]);
    }
}

