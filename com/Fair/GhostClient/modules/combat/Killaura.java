/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import com.Fair.GhostClient.settings.ModeSetting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Killaura
extends Module {
    public static EntityLivingBase target;
    private List<Entity> targets = new ArrayList<Entity>(0);
    private int index;
    private IntegerSetting cps = new IntegerSetting("CPS", 10.0, 1.0, 30.0);
    private IntegerSetting range = new IntegerSetting("range", 4.0, 1.0, 6.0);
    private IntegerSetting autoclockrange = new IntegerSetting("Autoblock range", 4.0, 1.0, 6.0);
    private IntegerSetting SwitchDelay = new IntegerSetting("SwitchDelay", 800.0, 1.0, 5000.0);
    private IntegerSetting turnSpeed = new IntegerSetting("TurnSpeed(percent)", 40.0, 1.0, 100.0);
    private IntegerSetting switchEntitys = new IntegerSetting("switchEntity", 2.0, 1.0, 5.0);
    private EnableSetting autoblock = new EnableSetting("AutoBlock", true);
    private EnableSetting players = new EnableSetting("players", true);
    private EnableSetting animals = new EnableSetting("animals", false);
    private EnableSetting mobs = new EnableSetting("mobs", false);
    private EnableSetting invisibles = new EnableSetting("invisibles", false);
    private EnableSetting villager = new EnableSetting("Villager", false);
    private EnableSetting esp = new EnableSetting("ESP", true);
    private EnableSetting random = new EnableSetting("Random", true);
    private EnableSetting targetHud = new EnableSetting("targetHud", true);
    private EnableSetting hover = new EnableSetting("Hover", true);
    private final ModeSetting Mode = new ModeSetting("Mode", "HurtTime", Arrays.asList("Delay", "HurtTime"), this);
    private final ModeSetting AttackMode = new ModeSetting("AttackMode", "Packet", Arrays.asList("Packet", "Attack"), this);
    private final ModeSetting switchMode = new ModeSetting("SwitchMode", "Switch", Arrays.asList("Switch", "Single"), this);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);
    public static float[] lastRotations;
    public static float lastYaw;
    public static float lastPitch;
    public static boolean shouldSetRot;
    public static int kgw;
    public static Entity lastTarget;
    private int rotyaw;
    private int rotpitch;
    private TimerUtils attackTimer = new TimerUtils();
    private TimerUtils switchTimer = new TimerUtils();
    private TimerUtils timerUtils = new TimerUtils();
    private TimerUtils timerUtils1 = new TimerUtils();
    private int Index;
    private float delay;
    private float virtualYaw;
    private float virtualPitch;
    private float pitchExpand;
    private boolean expandDirectionPitch;
    private float yawExpand;
    private boolean expandDirectionYaw;
    private int aps;
    private TimerUtils apsTimer = new TimerUtils();
    private int apsDelay;
    private boolean hitting;
    private int rangeExtend;

    public Killaura() {
        super("Killaura", 0, Category.Combat, false, "");
        this.getSetting().add(this.cps);
        this.getSetting().add(this.range);
        this.getSetting().add(this.autoclockrange);
        this.getSetting().add(this.SwitchDelay);
        this.getSetting().add(this.turnSpeed);
        this.getSetting().add(this.switchEntitys);
        this.getSetting().add(this.autoblock);
        this.getSetting().add(this.players);
        this.getSetting().add(this.animals);
        this.getSetting().add(this.mobs);
        this.getSetting().add(this.invisibles);
        this.getSetting().add(this.esp);
        this.getSetting().add(this.random);
        this.getSetting().add(this.targetHud);
        this.getSetting().add(this.hover);
        this.getSetting().add(this.Mode);
        this.getSetting().add(this.AttackMode);
        this.getSetting().add(this.switchMode);
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        super.enable();
        this.virtualPitch = Killaura.mc.thePlayer.rotationPitch;
        this.virtualYaw = Killaura.mc.thePlayer.rotationYaw;
        this.pitchExpand = 0.0f;
        this.yawExpand = 0.0f;
        this.expandDirectionYaw = false;
        this.expandDirectionPitch = false;
        this.setDelay();
        this.setApsDelay();
    }

    @Override
    public void disable() {
        super.disable();
        target = null;
        this.targets.clear();
        if (this.autoblock.getEnable() && this.hasSword() && Killaura.mc.thePlayer.isBlocking()) {
            this.unBlock();
        }
    }

    private void block() {
        if (Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState((int)Killaura.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
            if (Killaura.mc.playerController.sendUseItem((EntityPlayer)Killaura.mc.thePlayer, (World)Killaura.mc.theWorld, Killaura.mc.thePlayer.inventory.getCurrentItem())) {
                mc.getItemRenderer().resetEquippedProgress2();
            }
        }
    }

    private void unBlock() {
        if (Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState((int)Killaura.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            Killaura.mc.playerController.onStoppedUsingItem((EntityPlayer)Killaura.mc.thePlayer);
        }
    }

    private boolean hasSword() {
        if (Killaura.mc.thePlayer.inventory.getCurrentItem() != null) {
            return Killaura.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
        }
        return false;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (target != null) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Killaura.mc.fontRendererObj.drawStringWithShadow(target.getName(), (float)scaledResolution.getScaledWidth() / 2.0f - (float)Killaura.mc.fontRendererObj.getStringWidth(target.getName()) / 2.0f, (float)scaledResolution.getScaledHeight() / 2.0f - 33.0f, 0xFFFFFF);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            GL11.glDepthMask((boolean)false);
            OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
            int n = 0;
            while ((float)n < target.getMaxHealth() / 2.0f) {
                Killaura.mc.ingameGUI.func_175174_a((float)(scaledResolution.getScaledWidth() / 2) - target.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float)(n * 10), (float)(scaledResolution.getScaledHeight() / 2 - 20), 16, 0, 9, 9);
                ++n;
            }
            int n2 = 0;
            while ((float)n2 < target.getHealth() / 2.0f) {
                Killaura.mc.ingameGUI.func_175174_a((float)(scaledResolution.getScaledWidth() / 2) - target.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float)(n2 * 10), (float)(scaledResolution.getScaledHeight() / 2 - 20), 52, 0, 9, 9);
                ++n2;
            }
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GlStateManager.disableBlend();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
            RenderHelper.disableStandardItemLighting();
        }
    }

    private void setDelay() {
        this.delay = (int)(1000.0 / (double)this.aps) - new Random().nextInt(20);
    }

    private void setApsDelay() {
        this.apsDelay = 300 + new Random().nextInt(100);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void attack(EntityLivingBase entity) {
        if (Killaura.mc.thePlayer.isBlocking()) {
            Killaura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (entity != null) {
            if (entity.getDistanceToEntity((Entity)Killaura.mc.thePlayer) <= 4.0f) {
                Killaura.mc.thePlayer.swingItem();
                Killaura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
                Killaura.mc.thePlayer.onEnchantmentCritical((Entity)entity);
                Killaura.mc.thePlayer.onEnchantmentCritical((Entity)entity);
                this.hitting = true;
                return;
            }
        }
        Killaura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
        this.hitting = false;
    }

    @SubscribeEvent
    public void onRenderTick1(TickEvent.RenderTickEvent ev) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (target == null && this.autoblock.getEnable() && Killaura.mc.thePlayer.isBlocking() && !Mouse.isButtonDown((int)1) && this.hasSword()) {
            this.unBlock();
        }
        if (this.hasSword()) {
            if (target != null && this.autoblock.getEnable()) {
                this.block();
            }
        }
        this.targets = this.getTargets(this.range.getCurrent() + 1.0);
        if (this.targets.size() > 1 && this.targets.size() < (int)this.switchEntitys.getCurrent()) {
            if (this.switchTimer.delay((long)this.SwitchDelay.getCurrent())) {
                ++this.index;
                this.switchTimer.reset();
            } else if (target != null && Killaura.target.hurtTime != 0 && Killaura.mc.thePlayer.ticksExisted % 60 == 0) {
                ++this.index;
            }
        }
        if (Killaura.mc.thePlayer.ticksExisted % (int)this.SwitchDelay.getCurrent() == 0 && this.targets.size() > 1 && this.switchMode.getCurrent() == "Single") {
            if ((double)target.getDistanceToEntity((Entity)Killaura.mc.thePlayer) > this.range.getCurrent()) {
                ++this.index;
            } else if (Killaura.target.isDead) {
                ++this.index;
            }
        }
        if (target != null) {
            target = null;
        }
        if (!this.targets.isEmpty()) {
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
            lastTarget = target;
            target = (EntityLivingBase)this.targets.get(this.index);
            Killaura.mc.thePlayer.rotationYaw %= 360.0f;
            lastYaw = this.pq(target)[0];
            lastPitch = this.pq(target)[1];
            if (Killaura.mc.thePlayer.motionX == 0.0 & Killaura.mc.thePlayer.motionZ == 0.0) {
                this.rotyaw = (int)((double)this.rotyaw + ((float)this.rotyaw < lastYaw ? (double)Math.abs(lastYaw - (float)this.rotyaw) * (this.turnSpeed.getCurrent() / 100.0) : (double)(-Math.abs(lastYaw - (float)this.rotyaw)) * (this.turnSpeed.getCurrent() / 100.0) * (double)(Killaura.mc.thePlayer.ticksExisted % 2)));
                this.rotpitch = (int)((double)this.rotpitch + ((float)this.rotpitch < lastPitch ? (double)Math.abs(lastPitch - (float)this.rotpitch) * (this.turnSpeed.getCurrent() / 100.0) : (double)(-Math.abs(lastPitch - (float)this.rotpitch)) * (this.turnSpeed.getCurrent() / 100.0)));
                Random random = new Random();
                Killaura.mc.thePlayer.rotationYaw = (float)this.rotyaw + random.nextFloat() / 2.0f;
                Killaura.mc.thePlayer.rotationPitch = this.rotpitch;
            }
        }
    }

    public float[] pq(EntityLivingBase EntityLivingBase2) {
        if (EntityLivingBase2 != null) {
            double d = Killaura.mc.thePlayer.posX;
            double d2 = Killaura.mc.thePlayer.posY + (double)Killaura.mc.thePlayer.getEyeHeight();
            double d3 = Killaura.mc.thePlayer.posZ;
            double d4 = EntityLivingBase2.posX;
            double d5 = EntityLivingBase2.posY + (double)(EntityLivingBase2.height / 2.0f);
            double d6 = EntityLivingBase2.posZ;
            double d7 = d - d4;
            double d8 = d2 - d5;
            double d9 = d3 - d6;
            double d10 = Math.sqrt(Math.pow(d7, 2.0) + Math.pow(d9, 2.0));
            float f = (float)(Math.toDegrees(Math.atan2(d9, d7)) + 90.0);
            float f2 = (float)Math.toDegrees(Math.atan2(d10, d8));
            return new float[]{(float)((double)f + (new Random().nextBoolean() ? Math.random() : -Math.random())), (float)((double)(90.0f - f2) + (new Random().nextBoolean() ? Math.random() : -Math.random()))};
        }
        return null;
    }

    public static float[] d(float[] arrf, float[] arrf2, float f) {
        double d = Killaura.getAngleDifference(arrf2[0], arrf[0]);
        double d2 = Killaura.getAngleDifference(arrf2[1], arrf[1]);
        arrf[0] = (float)((double)arrf[0] + (d > (double)f ? (double)f : (d < (double)(-f) ? (double)(-f) : d)));
        arrf[1] = (float)((double)arrf[1] + (d2 > (double)f ? (double)f : (d2 < (double)(-f) ? (double)(-f) : d2)));
        return arrf;
    }

    public static double getAngleDifference(double d, double d2) {
        return ((d - d2) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (target != null) {
            if (this.shouldAttack()) {
                if (this.hasSword() && Killaura.mc.thePlayer.isBlocking()) {
                    if (this.canAttack((Entity)target)) {
                        this.unBlock();
                    }
                }
                Killaura.mc.thePlayer.swingItem();
                if (this.AttackMode.getCurrent() == "Packet") {
                    mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.ATTACK));
                } else if (this.AttackMode.getCurrent() == "Attach") {
                    Killaura.mc.playerController.attackEntity((EntityPlayer)Killaura.mc.thePlayer, (Entity)target);
                }
                this.attackTimer.reset();
            }
            if (!Killaura.mc.thePlayer.isBlocking() && this.hasSword() && this.autoblock.getEnable()) {
                this.block();
            }
        }
    }

    public List<Entity> getTargets(Double value) {
        ArrayList<Entity> ents = new ArrayList<Entity>();
        for (Entity ent : Killaura.mc.theWorld.loadedEntityList) {
            if (!((double)Killaura.mc.thePlayer.getDistanceToEntity(ent) <= value) || !this.canAttack(ent)) continue;
            if (ents.size() >= (int)this.switchEntitys.getCurrent()) {
                ents.remove(0);
            }
            ents.add(ent);
        }
        return ents;
    }

    public boolean canAttack(Entity e) {
        if (e == Killaura.mc.thePlayer) {
            return false;
        }
        if (!e.isEntityAlive()) {
            return false;
        }
        if (e instanceof EntityPlayer && this.players.getEnable()) {
            return true;
        }
        if (e instanceof EntityMob || e instanceof EntityBat || e instanceof EntityWaterMob && this.mobs.getEnable()) {
            return true;
        }
        if (e instanceof EntityAnimal && this.animals.getEnable()) {
            return true;
        }
        if (e.isInvisible() && this.invisibles.getEnable() && e instanceof EntityPlayer) {
            return true;
        }
        return e instanceof EntityVillager && this.villager.getEnable();
    }

    private boolean shouldAttack() {
        return this.attackTimer.isDelayComplete(1000.0 / this.cps.getCurrent());
    }

    static {
        lastTarget = null;
    }
}

