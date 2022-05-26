/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.modules.misc;

import com.Fair.GhostClient.Utils.ReflectUtil;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Projectiles
extends Module {
    private MovingObjectPosition blockCollision;
    private MovingObjectPosition entityCollision;
    private EntityLivingBase entity;
    private AxisAlignedBB aim;

    public Projectiles() {
        super("Projectiles", 0, Category.Misc, false, "");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        int item;
        EntityPlayerSP player = Projectiles.mc.thePlayer;
        ItemStack stack = player.inventory.getCurrentItem();
        if (Projectiles.mc.thePlayer.inventory.getCurrentItem() != null && ((item = Item.getIdFromItem((Item)Projectiles.mc.thePlayer.getHeldItem().getItem())) == 261 || item == 368 || item == 332 || item == 344)) {
            double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) - Math.cos(Math.toRadians(player.rotationYaw)) * (double)0.16f;
            double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) + (double)player.getEyeHeight() - 0.1;
            double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) - Math.sin(Math.toRadians(player.rotationYaw)) * (double)0.16f;
            double itemBow = stack.getItem() instanceof ItemBow ? 1.0 : (double)0.4f;
            double yaw = Math.toRadians(player.rotationYaw);
            double pitch = Math.toRadians(player.rotationPitch);
            double trajectoryX = -Math.sin(yaw) * Math.cos(pitch) * itemBow;
            double trajectoryY = -Math.sin(pitch) * itemBow;
            double trajectoryZ = Math.cos(yaw) * Math.cos(pitch) * itemBow;
            double trajectory = Math.sqrt(trajectoryX * trajectoryX + trajectoryY * trajectoryY + trajectoryZ * trajectoryZ);
            trajectoryX /= trajectory;
            trajectoryY /= trajectory;
            trajectoryZ /= trajectory;
            if (stack.getItem() instanceof ItemBow) {
                float bowPower = (float)(72000 - player.getItemInUseCount()) / 20.0f;
                if ((bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f) > 1.0f) {
                    bowPower = 1.0f;
                }
                trajectoryX *= (double)(bowPower *= 3.0f);
                trajectoryY *= (double)bowPower;
                trajectoryZ *= (double)bowPower;
            } else {
                trajectoryX *= 1.5;
                trajectoryY *= 1.5;
                trajectoryZ *= 1.5;
            }
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)2.0f);
            double gravity = stack.getItem() instanceof ItemBow ? 0.05 : 0.03;
            GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.2f, (float)0.5f);
            GL11.glBegin((int)3);
            for (int i = 0; i < 2000; ++i) {
                GL11.glVertex3d((double)(posX - Projectiles.getRenderPosX()), (double)(posY - Projectiles.getRenderPosY()), (double)(posZ - Projectiles.getRenderPosZ()));
                posX += trajectoryX * 0.1;
                posY += trajectoryY * 0.1;
                posZ += trajectoryZ * 0.1;
                trajectoryX *= 0.999;
                trajectoryY *= 0.999;
                trajectoryZ *= 0.999;
                trajectoryY -= gravity * 0.1;
                Vec3 vec = new Vec3(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
                this.blockCollision = Projectiles.mc.theWorld.rayTraceBlocks(vec, new Vec3(posX, posY, posZ));
                for (Entity o : Projectiles.mc.theWorld.getLoadedEntityList()) {
                    if (!(o instanceof EntityLivingBase) || o instanceof EntityPlayerSP) continue;
                    this.entity = (EntityLivingBase)o;
                    AxisAlignedBB entityBoundingBox = this.entity.getEntityBoundingBox().expand(1.3, 0.3, 0.3);
                    this.entityCollision = entityBoundingBox.calculateIntercept(vec, new Vec3(posX, posY, posZ));
                    if (this.entityCollision != null) {
                        this.blockCollision = this.entityCollision;
                    }
                    if (this.entityCollision != null) {
                        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.2f, (float)0.5f);
                    }
                    if (this.entityCollision == null) continue;
                    this.blockCollision = this.entityCollision;
                }
                if (this.blockCollision != null) break;
            }
            GL11.glEnd();
            double renderX = posX - Projectiles.getRenderPosX();
            double renderY = posY - Projectiles.getRenderPosY();
            double renderZ = posZ - Projectiles.getRenderPosZ();
            GL11.glPushMatrix();
            GL11.glTranslated((double)(renderX - 0.5), (double)(renderY - 0.5), (double)(renderZ - 0.5));
            switch (this.blockCollision.sideHit.getIndex()) {
                case 2: 
                case 3: {
                    GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    this.aim = new AxisAlignedBB(0.0, 0.5, -1.0, 1.0, 0.45, 0.0);
                    break;
                }
                case 4: 
                case 5: {
                    GlStateManager.rotate((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    this.aim = new AxisAlignedBB(0.0, -0.5, 0.0, 1.0, -0.45, 1.0);
                    break;
                }
                default: {
                    this.aim = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 0.45, 1.0);
                }
            }
            this.drawBox(this.aim);
            RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)this.aim);
            GL11.glPopMatrix();
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2848);
            GL11.glPopMatrix();
        }
        for (Object object : Projectiles.mc.theWorld.loadedEntityList) {
            if (!(object instanceof EntityArrow)) continue;
            EntityArrow arrow = (EntityArrow)object;
            double posX = arrow.field_70165_t;
            double posY = arrow.field_70163_u;
            double posZ = arrow.field_70161_v;
            double motionX = arrow.field_70159_w;
            double motionY = arrow.field_70181_x;
            double motionZ = arrow.field_70179_y;
            MovingObjectPosition landingPosition2 = null;
            boolean hasLanded2 = false;
            float gravity2 = 0.05f;
            Projectiles.enableRender3D(true);
            this.setColor(3196666);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            for (int limit2 = 0; !hasLanded2 && limit2 < 300; ++limit2) {
                BlockPos var20;
                Block var21;
                Vec3 posBefore2 = new Vec3(posX, posY, posZ);
                Vec3 posAfter2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                landingPosition2 = Projectiles.mc.theWorld.rayTraceBlocks(posBefore2, posAfter2, false, true, false);
                if (landingPosition2 != null) {
                    hasLanded2 = true;
                }
                if ((var21 = Projectiles.mc.theWorld.getBlockState(var20 = new BlockPos(posX += motionX, posY += motionY, posZ += motionZ)).getBlock()).getMaterial() == Material.water) {
                    motionX *= 0.6;
                    motionY *= 0.6;
                    motionZ *= 0.6;
                } else {
                    motionX *= 0.99;
                    motionY *= 0.99;
                    motionZ *= 0.99;
                }
                motionY -= (double)0.05f;
                GL11.glVertex3d((double)(posX - Projectiles.getRenderPosX()), (double)(posY - Projectiles.getRenderPosY()), (double)(posZ - Projectiles.getRenderPosZ()));
            }
            GL11.glEnd();
            Projectiles.disableRender3D(true);
        }
    }

    public static void enableRender3D(boolean disableDepth) {
        if (disableDepth) {
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
        }
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)1.0f);
    }

    public static void disableRender3D(boolean enableDepth) {
        if (enableDepth) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void drawBox(AxisAlignedBB bb) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
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
}

