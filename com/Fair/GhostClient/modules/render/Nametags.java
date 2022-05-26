/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.Utils.Nameplate;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.settings.KeyBindSetting;
import java.awt.Color;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Nametags
extends Module {
    private Queue<Nameplate> tags;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    @SubscribeEvent
    public void onPreRender(RenderPlayerEvent.Pre event) {
        double v = 0.3;
        Scoreboard sb = event.entityPlayer.getWorldScoreboard();
        ScoreObjective sbObj = sb.getObjectiveInDisplaySlot(2);
        if (sbObj != null && !event.entityPlayer.getDisplayNameString().equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString()) && event.entityPlayer.getDistanceSqToEntity((Entity)Minecraft.getMinecraft().thePlayer) < 100.0) {
            v *= 2.0;
        }
        if (!event.entityPlayer.getDisplayName().equals(Minecraft.getMinecraft().thePlayer.getDisplayName())) {
            Nameplate np = new Nameplate(event.entityPlayer.getDisplayNameString(), event.x, event.y, event.z, event.entityLiving);
            np.renderNewPlate(new Color(100, 100, 100));
        }
    }

    public Nametags() {
        super("Nametags", 0, Category.Render, false, "");
        this.getSetting().add(this.KeyBind);
    }
}

