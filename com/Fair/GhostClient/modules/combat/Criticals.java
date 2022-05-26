/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.network.play.server.S0BPacketAnimation
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Utils.Connection;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.ModeSetting;
import java.lang.reflect.Field;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Criticals
extends Module {
    private final ModeSetting Mode = new ModeSetting("Mode", "AAC5", Arrays.asList("Packet", "Hypixel", "Jump", "PJump", "AAC5", "NoGround"), this);
    int targetid;
    boolean cancelSomePackets;
    public TimerUtils timer = new TimerUtils();

    public Criticals() {
        super("Criticals", 0, Category.Combat, false, "");
        this.getSetting().add(this.Mode);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.Mode.getCurrent().equals("AAC5")) {
            if (Criticals.mc.thePlayer.onGround) {
                if (Criticals.mc.thePlayer.field_70737_aN > 0 && Criticals.mc.thePlayer.field_70737_aN <= 6) {
                    Criticals.mc.thePlayer.motionX *= 0.600151164;
                    Criticals.mc.thePlayer.motionZ *= 0.600151164;
                }
                if (Criticals.mc.thePlayer.field_70737_aN > 0 && Criticals.mc.thePlayer.field_70737_aN <= 4) {
                    Criticals.mc.thePlayer.motionX *= 0.800151164;
                    Criticals.mc.thePlayer.motionZ *= 0.800151164;
                }
            } else if (Criticals.mc.thePlayer.field_70737_aN > 0 && Criticals.mc.thePlayer.field_70737_aN <= 9) {
                Criticals.mc.thePlayer.motionX *= 0.8001421204;
                Criticals.mc.thePlayer.motionZ *= 0.8001421204;
            }
        }
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        S0BPacketAnimation s08;
        if (this.Mode.getCurrent().equals("NoGround")) {
            if (Criticals.mc.thePlayer.onGround && side == Connection.Side.OUT) {
                if (packet instanceof C02PacketUseEntity) {
                    C02PacketUseEntity attack = (C02PacketUseEntity)packet;
                    if (attack.getAction() == C02PacketUseEntity.Action.ATTACK) {
                        Field crit = ReflectionHelper.findField(C02PacketUseEntity.class, (String[])new String[]{"entityId", "field_149567_a"});
                        try {
                            if (!crit.isAccessible()) {
                                crit.setAccessible(true);
                            }
                            this.targetid = crit.getInt(attack);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        if (this.Mode.getCurrent().equals("Packet")) {
                            if (Criticals.mc.thePlayer.isCollidedVertically && this.timer.isDelay(500L)) {
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.0627, Criticals.mc.thePlayer.posZ, false));
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                                Entity entity = attack.getEntityFromWorld((World)Criticals.mc.theWorld);
                                if (entity != null) {
                                    Criticals.mc.thePlayer.onCriticalHit(entity);
                                }
                                this.timer.setLastMS();
                                this.cancelSomePackets = true;
                            }
                        } else if (this.Mode.getCurrent().equals("Jump")) {
                            if (this.canJump()) {
                                Criticals.mc.thePlayer.jump();
                            }
                        } else if (this.Mode.getCurrent().equals("PJump")) {
                            if (this.canJump()) {
                                Criticals.mc.thePlayer.jump();
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.0031311231111, Criticals.mc.thePlayer.posZ, false));
                            }
                        } else if (this.Mode.getCurrent().equals("Hypixel") && Criticals.mc.thePlayer.isCollidedVertically && this.timer.isDelay(500L)) {
                            Criticals.hypixelCrit();
                            this.timer.setLastMS();
                            this.cancelSomePackets = true;
                        }
                    }
                } else if (this.Mode.getCurrent().equals("Packet") && packet instanceof C03PacketPlayer && this.cancelSomePackets) {
                    this.cancelSomePackets = false;
                    return false;
                }
            }
        } else if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer p = (C03PacketPlayer)packet;
            Field field = ReflectionHelper.findField(C03PacketPlayer.class, (String[])new String[]{"onGround", "field_149474_g"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.setBoolean(p, false);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        if (side == Connection.Side.IN && packet instanceof S0BPacketAnimation && (s08 = (S0BPacketAnimation)packet).getAnimationType() == 4 && s08.getEntityID() == this.targetid) {
            Criticals.report("Crit!");
        }
        return true;
    }

    public static void component(ChatComponentText component) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().ingameGUI.getChatGUI() == null) {
            return;
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("").appendSibling((IChatComponent)component));
    }

    public static void message(Object message) {
        Criticals.component(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "[]\u00a77" + message));
    }

    public static void report(String message) {
        Criticals.message(EnumChatFormatting.GREEN + message);
    }

    public static void hypixelCrit() {
        for (double offset : new double[]{0.0212622959183674, 0.0, 0.0521, 0.02474, 0.01, 0.001}) {
            Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + offset, Criticals.mc.thePlayer.posZ, false));
        }
    }

    boolean canJump() {
        if (Criticals.mc.thePlayer.func_70617_f_()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isInWater()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isInLava()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isSneaking()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isRiding()) {
            return false;
        }
        return !Criticals.mc.thePlayer.func_70644_a(Potion.blindness);
    }
}

