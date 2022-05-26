/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Timer
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.modules.combat.AutoClicker;
import com.Fair.GhostClient.modules.combat.Reach;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class HitBox
extends Module {
    private static MovingObjectPosition mv;
    private static boolean showHitBox;
    private static Field timerField;
    private static IntegerSetting Multiplier;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public HitBox() {
        super("HitBox", 0, Category.Combat, false, "");
        this.getSetting().add(Multiplier);
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        HitBox.gmo(1.0f);
    }

    @SubscribeEvent
    public void on(TickEvent.PlayerTickEvent ev) {
        if (Client.moduleManager.getModule("AutoClicker").getState() && AutoClicker.Left_Click.getEnable() && Mouse.isButtonDown((int)0) && mv != null) {
            HitBox.mc.objectMouseOver = mv;
        }
    }

    @SubscribeEvent
    public void onMouse(MouseEvent e) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (e.button == 0 && e.buttonstate && mv != null) {
            HitBox.mc.objectMouseOver = mv;
        }
        if (Reach.ReachEnable && e.button >= 0 && e.buttonstate) {
            Reach.call();
        }
    }

    public static Timer getTimer() {
        try {
            return (Timer)timerField.get(mc);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void gmo(float partialTicks) {
        if (mc.getRenderViewEntity() != null && HitBox.mc.theWorld != null) {
            HitBox.mc.pointedEntity = null;
            Entity pE = null;
            double d0 = 3.0;
            mv = mc.getRenderViewEntity().rayTrace(d0, partialTicks);
            double d2 = d0;
            Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(partialTicks);
            if (mv != null) {
                d2 = HitBox.mv.hitVec.distanceTo(vec3);
            }
            Vec3 vec4 = mc.getRenderViewEntity().getLook(partialTicks);
            Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
            Vec3 vec6 = null;
            float f1 = 1.0f;
            List list = HitBox.mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
            double d3 = d2;
            for (Object o : list) {
                double d4;
                Entity entity = (Entity)o;
                if (!entity.canBeCollidedWith()) continue;
                float ex = (float)((double)entity.getCollisionBorderSize() * HitBox.exp(entity));
                AxisAlignedBB ax = entity.getEntityBoundingBox().expand((double)ex, (double)ex, (double)ex);
                MovingObjectPosition mop = ax.calculateIntercept(vec3, vec5);
                if (ax.isVecInside(vec3)) {
                    if (!(0.0 < d3) && d3 != 0.0) continue;
                    pE = entity;
                    vec6 = mop == null ? vec3 : mop.hitVec;
                    d3 = 0.0;
                    continue;
                }
                if (mop == null || !((d4 = vec3.distanceTo(mop.hitVec)) < d3) && d3 != 0.0) continue;
                if (entity == HitBox.mc.getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                    if (d3 != 0.0) continue;
                    pE = entity;
                    vec6 = mop.hitVec;
                    continue;
                }
                pE = entity;
                vec6 = mop.hitVec;
                d3 = d4;
            }
            if (pE != null && (d3 < d2 || mv == null)) {
                mv = new MovingObjectPosition(pE, vec6);
                if (pE instanceof EntityLivingBase || pE instanceof EntityItemFrame) {
                    HitBox.mc.pointedEntity = pE;
                }
            }
        }
    }

    public static double exp(Entity en) {
        return Client.moduleManager.getModule((String)"HitBox").state ? Multiplier.getCurrent() : 1.0;
    }

    static {
        showHitBox = true;
        timerField = null;
        Multiplier = new IntegerSetting("Multiplier", 1.2, 1.0, 6.0);
    }
}

