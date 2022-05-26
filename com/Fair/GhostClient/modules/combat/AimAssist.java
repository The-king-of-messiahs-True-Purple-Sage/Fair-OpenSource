/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package com.Fair.GhostClient.modules.combat;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.modules.combat.AutoClicker;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AimAssist
extends Module {
    private static final HashMap<EntityPlayer, Long> newEnt = new HashMap();
    private IntegerSetting speed = new IntegerSetting("Speed", 40.0, 1.0, 100.0);
    private IntegerSetting compliment = new IntegerSetting("Compliment", 15.0, 1.0, 100.0);
    private IntegerSetting fov = new IntegerSetting("FOV", 80.0, 1.0, 360.0);
    private IntegerSetting distance = new IntegerSetting("Distance", 4.3, 1.0, 10.0);
    private EnableSetting clickAim = new EnableSetting("ClickAim", true);
    private EnableSetting Break_Blocks = new EnableSetting("Break Blocks", true);
    private EnableSetting players = new EnableSetting("players", true);
    private EnableSetting animals = new EnableSetting("animals", false);
    private EnableSetting mobs = new EnableSetting("mobs", false);
    private EnableSetting invisibles = new EnableSetting("invisibles", false);
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public AimAssist() {
        super("AimAssist", 0, Category.Combat, false, "");
        this.getSetting().add(this.speed);
        this.getSetting().add(this.compliment);
        this.getSetting().add(this.fov);
        this.getSetting().add(this.distance);
        this.getSetting().add(this.clickAim);
        this.getSetting().add(this.players);
        this.getSetting().add(this.animals);
        this.getSetting().add(this.mobs);
        this.getSetting().add(this.invisibles);
        this.getSetting().add(this.KeyBind);
    }

    public static boolean autoClickerClicking() {
        Module autoclicker = Client.moduleManager.getModule("AutoClicker");
        if (autoclicker.state) {
            return AutoClicker.Left_Click.getEnable() && Mouse.isButtonDown((int)0);
        }
        return false;
    }

    public static double fovFromEntity(Entity en) {
        return ((double)(AimAssist.mc.thePlayer.rotationYaw - AimAssist.fovToEntity(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public static float fovToEntity(Entity ent) {
        double x = ent.posX - AimAssist.mc.thePlayer.posX;
        double z = ent.posZ - AimAssist.mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.2957795;
        return (float)(yaw * -1.0);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        block9: {
            double n;
            Entity en;
            block8: {
                Module autoclicker;
                block7: {
                    Block bl;
                    BlockPos p;
                    if (!Tools.currentScreenMinecraft()) {
                        return;
                    }
                    if (!Tools.isPlayerInGame()) {
                        return;
                    }
                    if (this.Break_Blocks.getEnable() && AimAssist.mc.objectMouseOver != null && (p = AimAssist.mc.objectMouseOver.getBlockPos()) != null && (bl = AimAssist.mc.theWorld.getBlockState(p).getBlock()) != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof Block) {
                        return;
                    }
                    autoclicker = Client.moduleManager.getModule("AutoClicker");
                    if (!this.clickAim.getEnable()) break block7;
                    if (this.autoClickerClicking()) break block8;
                }
                if ((!Mouse.isButtonDown((int)0) || autoclicker.state) && this.clickAim.getEnable()) break block9;
            }
            if ((en = this.getEnemy()) != null && ((n = AimAssist.fovFromEntity(en)) > 1.0 || n < -1.0)) {
                double complimentSpeed = n * (ThreadLocalRandom.current().nextDouble(this.compliment.getCurrent() - 1.47328, this.compliment.getCurrent() + 2.48293) / 100.0);
                double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble(this.speed.getCurrent() - 4.723847, this.speed.getCurrent());
                float val = (float)(-(complimentSpeed + n / (101.0 - (double)((float)ThreadLocalRandom.current().nextDouble(this.speed.getCurrent() - 4.723847, this.speed.getCurrent())))));
                AimAssist.mc.thePlayer.rotationYaw += val;
            }
        }
    }

    public static boolean bot(Entity en) {
        if (!Tools.isPlayerInGame() || AimAssist.mc.currentScreen != null) {
            return false;
        }
        if (!newEnt.isEmpty() && newEnt.containsKey(en)) {
            return true;
        }
        if (en.getName().startsWith("\u951f\u65a4\u62f7c")) {
            return true;
        }
        String n = en.getDisplayName().getUnformattedText();
        if (n.contains("\u951f\u65a4\u62f7")) {
            return n.contains("[NPC] ");
        }
        if (n.isEmpty() && en.getName().isEmpty()) {
            return true;
        }
        if (n.length() == 10) {
            char[] var4;
            int num = 0;
            int let = 0;
            for (char c : var4 = n.toCharArray()) {
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        return false;
                    }
                    ++let;
                    continue;
                }
                if (!Character.isDigit(c)) {
                    return false;
                }
                ++num;
            }
            return num >= 2 && let >= 2;
        }
        return false;
    }

    public Entity getEnemy() {
        Entity en;
        int fov2 = (int)this.fov.getCurrent();
        Iterator var2 = AimAssist.mc.theWorld.loadedEntityList.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while ((en = (Entity)var2.next()) == AimAssist.mc.thePlayer || en.isDead || !this.invisibles.getEnable() && en.isInvisible() || (double)AimAssist.mc.thePlayer.getDistanceToEntity(en) > this.distance.getCurrent() || AimAssist.bot(en));
        if (en instanceof EntityPlayer && this.players.getEnable()) {
            return en;
        }
        if (en instanceof EntityAnimal && this.animals.getEnable()) {
            return en;
        }
        if (en instanceof EntityMob && this.mobs.getEnable()) {
            return en;
        }
        return null;
    }
}

