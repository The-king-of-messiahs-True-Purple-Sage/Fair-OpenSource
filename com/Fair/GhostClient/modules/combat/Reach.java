/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.modules.combat.AutoClicker;
import com.Fair.GhostClient.modules.combat.HitBox;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Reach
extends Module {
    public static IntegerSetting maxRange = new IntegerSetting("Max Range", 3.3, 3.0, 6.0);
    public static IntegerSetting minRange = new IntegerSetting("Min Range", 3.1, 3.0, 6.0);
    private static EnableSetting weapon_only = new EnableSetting("weapon_only", false);
    private static EnableSetting moving_only = new EnableSetting("moving_only", false);
    private static EnableSetting sprint_only = new EnableSetting("sprint_only", false);
    private static EnableSetting hit_through_blocks = new EnableSetting("ThroughBlocks", false);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);
    public static double reachDistance = 6.0;
    private static final Random rand = new Random();
    public static boolean ReachEnable = false;

    public Reach() {
        super("Reach", 0, Category.Combat, false, "");
        this.getSetting().add(maxRange);
        this.getSetting().add(minRange);
        this.getSetting().add(weapon_only);
        this.getSetting().add(moving_only);
        this.getSetting().add(sprint_only);
        reachDistance = maxRange.getCurrent();
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        reachDistance = maxRange.getCurrent();
        ReachEnable = true;
    }

    @Override
    public void disable() {
        ReachEnable = false;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (!Client.moduleManager.getModule("AutoClicker").getState() && !AutoClicker.Left_Click.getEnable()) {
            return;
        }
        if (Client.moduleManager.getModule("AutoClicker").getState() && AutoClicker.Left_Click.getEnable() && Mouse.isButtonDown((int)0)) {
            Reach.call();
        }
        if (Client.moduleManager.getModule("HitBox").getState() && Mouse.isButtonDown((int)0)) {
            Reach.call();
        }
    }

    @SubscribeEvent
    public void onMouse(MouseEvent ev) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (Client.moduleManager.getModule("AutoClicker").getState() && AutoClicker.Left_Click.getEnable() && Mouse.isButtonDown((int)0)) {
            Reach.call();
        }
        if (ev.button >= 0 && ev.buttonstate) {
            Reach.call();
        }
    }

    public static double ranModuleVal(Setting a, Setting b, Random r) {
        return ((IntegerSetting)a).getCurrent() == ((IntegerSetting)b).getCurrent() ? ((IntegerSetting)a).getCurrent() : ((IntegerSetting)a).getCurrent() + r.nextDouble() * (((IntegerSetting)b).getCurrent() - ((IntegerSetting)a).getCurrent());
    }

    public static Random getRand() {
        return rand;
    }

    public static boolean call() {
        BlockPos p;
        if (!Tools.isPlayerInGame()) {
            return false;
        }
        if (weapon_only.getEnable() && !Tools.isPlayerHoldingWeapon()) {
            return false;
        }
        if (moving_only.getEnable() && (double)Reach.mc.thePlayer.field_70701_bs == 0.0 && (double)Reach.mc.thePlayer.field_70702_br == 0.0) {
            return false;
        }
        if (sprint_only.getEnable() && !Reach.mc.thePlayer.isSprinting()) {
            return false;
        }
        if (!hit_through_blocks.getEnable() && Reach.mc.objectMouseOver != null && (p = Reach.mc.objectMouseOver.getBlockPos()) != null && Reach.mc.theWorld.getBlockState(p).getBlock() != Blocks.air) {
            return false;
        }
        double distance = Reach.ranModuleVal(maxRange, minRange, Reach.getRand());
        Object[] o = Reach.zz(distance, 0.0);
        if (o == null) {
            return false;
        }
        Entity en = (Entity)o[0];
        Reach.mc.objectMouseOver = new MovingObjectPosition(en, (Vec3)o[1]);
        Reach.mc.pointedEntity = en;
        return true;
    }

    private static Object[] zz(double zzD, double zzE) {
        if (!Client.moduleManager.getModule("Reach").getState()) {
            zzD = Reach.mc.playerController.extendedReach() ? 6.0 : 3.0;
        }
        Entity entity1 = mc.getRenderViewEntity();
        Entity entity = null;
        if (entity1 == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        Vec3 eyes_positions = entity1.getPositionEyes(1.0f);
        Vec3 look = entity1.getLook(1.0f);
        Vec3 new_eyes_pos = eyes_positions.addVector(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD);
        Vec3 zz6 = null;
        List zz8 = Reach.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().addCoord(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD).expand(1.0, 1.0, 1.0));
        double zz9 = zzD;
        for (Object o : zz8) {
            double zz15;
            Entity zz11 = (Entity)o;
            if (!zz11.canBeCollidedWith()) continue;
            float ex = (float)((double)zz11.getCollisionBorderSize() * HitBox.exp(zz11));
            AxisAlignedBB zz13 = zz11.getEntityBoundingBox().expand((double)ex, (double)ex, (double)ex);
            zz13 = zz13.expand(zzE, zzE, zzE);
            MovingObjectPosition zz14 = zz13.calculateIntercept(eyes_positions, new_eyes_pos);
            if (zz13.isVecInside(eyes_positions)) {
                if (!(0.0 < zz9) && zz9 != 0.0) continue;
                entity = zz11;
                zz6 = zz14 == null ? eyes_positions : zz14.hitVec;
                zz9 = 0.0;
                continue;
            }
            if (zz14 == null || !((zz15 = eyes_positions.distanceTo(zz14.hitVec)) < zz9) && zz9 != 0.0) continue;
            if (zz11 == entity1.ridingEntity) {
                if (zz9 != 0.0) continue;
                entity = zz11;
                zz6 = zz14.hitVec;
                continue;
            }
            entity = zz11;
            zz6 = zz14.hitVec;
            zz9 = zz15;
        }
        if (zz9 < zzD && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity != null && zz6 != null) {
            return new Object[]{entity, zz6};
        }
        return null;
    }
}

