/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.Fair.GhostClient.modules.render;

import com.Fair.GhostClient.Utils.ReflectUtil;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.Utils.nBlockPos;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.EnableSetting;
import com.Fair.GhostClient.settings.IntegerSetting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Xray
extends Module {
    public static EnableSetting cave = new EnableSetting("Cave", true);
    public static EnableSetting coal = new EnableSetting("Coal", true);
    public static EnableSetting iron = new EnableSetting("Iron", true);
    public static EnableSetting lapis = new EnableSetting("Lapis", true);
    public static EnableSetting emerald = new EnableSetting("Emerald", true);
    public static EnableSetting redstone = new EnableSetting("Redstone", true);
    public static EnableSetting gold = new EnableSetting("Gold", true);
    public static EnableSetting diammond = new EnableSetting("Diammond", true);
    public static IntegerSetting depth = new IntegerSetting("Depth", 2.0, 1.0, 5.0);
    public static IntegerSetting refresh_timer = new IntegerSetting("RefreshDelay", 5.0, 0.0, 50.0);
    public static EnableSetting radiusOn = new EnableSetting("RadiusEnabled", true);
    public static IntegerSetting radius = new IntegerSetting("Radius", 10.0, 5.0, 100.0);
    public static EnableSetting limitEnabled = new EnableSetting("RenderLimitEnabled", true);
    public static IntegerSetting limit = new IntegerSetting("RenderLimit", 10.0, 5.0, 100.0);
    private final TimerUtils refresh = new TimerUtils();
    public nBlockPos pos = new nBlockPos();
    public static List<BlockPos> toRender = new ArrayList<BlockPos>();

    public Xray() {
        super("Xray", 0, Category.Render, false, "");
        this.getSetting().add(cave);
        this.getSetting().add(coal);
        this.getSetting().add(iron);
        this.getSetting().add(lapis);
        this.getSetting().add(emerald);
        this.getSetting().add(redstone);
        this.getSetting().add(gold);
        this.getSetting().add(diammond);
        this.getSetting().add(depth);
        this.getSetting().add(refresh_timer);
        this.getSetting().add(radiusOn);
        this.getSetting().add(radius);
        this.getSetting().add(limitEnabled);
        this.getSetting().add(limit);
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

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        if (this.refresh.isDelayComplete(refresh_timer.getCurrent())) {
            WorldClient world = Xray.mc.theWorld;
            EntityPlayerSP player = Xray.mc.thePlayer;
            if (world != null && player != null) {
                int sx = (int)player.posX - (int)radius.getCurrent();
                int sz = (int)player.posZ - (int)radius.getCurrent();
                int endX = (int)player.posX + (int)radius.getCurrent();
                int endZ = (int)player.posZ + (int)radius.getCurrent();
                for (int x = sx; x <= endX; ++x) {
                    this.pos.setX(x);
                    for (int z = sz; z <= endZ; ++z) {
                        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        if (!chunk.isLoaded()) continue;
                        this.pos.setZ(z);
                        for (int y = 0; y <= 255; ++y) {
                            BlockPos poss;
                            this.pos.setY(y);
                            IBlockState blockState = chunk.getBlockState((BlockPos)this.pos);
                            Block block = blockState.getBlock();
                            if (block == Blocks.air || toRender.contains(poss = new BlockPos(x, y, z)) || !this.test(poss) || (double)toRender.size() > limit.getCurrent() && limitEnabled.getEnable()) continue;
                            toRender.add(poss);
                        }
                    }
                }
                List<Object> list = toRender;
                toRender = list = list.stream().filter(this::test).collect(Collectors.toList());
                this.refresh.reset();
            }
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if (!Tools.currentScreenMinecraft()) {
            return;
        }
        for (BlockPos blockPos : toRender) {
            this.renderBlock(blockPos);
        }
    }

    private void renderBlock(BlockPos pos) {
        double x = (double)pos.func_177958_n() - Xray.getRenderPosX();
        double y = (double)pos.func_177956_o() - Xray.getRenderPosY();
        double z = (double)pos.func_177952_p() - Xray.getRenderPosZ();
        float[] color = Xray.getColor(pos);
        Xray.drawOutlinedBlockESP(x, y, z, color[0], color[1], color[2], 0.25f, 2.5f);
    }

    public static float[] getColor(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return new float[]{0.0f, 1.0f, 1.0f};
        }
        if (Blocks.lapis_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 1.0f};
        }
        if (Blocks.iron_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 1.0f};
        }
        if (Blocks.gold_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 0.0f};
        }
        if (Blocks.coal_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        if (Blocks.emerald_ore.equals(block)) {
            return new float[]{0.0f, 1.0f, 0.0f};
        }
        if (Blocks.redstone_ore.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return new float[]{1.0f, 0.0f, 0.0f};
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)lineWidth);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        Xray.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }

    private Boolean oreTest(BlockPos origPos, Double depth) {
        ArrayList<BlockPos> posesNew = new ArrayList<BlockPos>();
        ArrayList<Object> posesLast = new ArrayList<BlockPos>(Collections.singletonList(origPos));
        ArrayList<BlockPos> finalList = new ArrayList<BlockPos>();
        int i = 0;
        while ((double)i < depth) {
            for (BlockPos blockPos2 : posesLast) {
                posesNew.add(blockPos2.up());
                posesNew.add(blockPos2.down());
                posesNew.add(blockPos2.north());
                posesNew.add(blockPos2.south());
                posesNew.add(blockPos2.west());
                posesNew.add(blockPos2.east());
            }
            for (BlockPos blockPos3 : posesNew) {
                if (!posesLast.contains(blockPos3)) continue;
                posesNew.remove(blockPos3);
            }
            posesLast = posesNew;
            finalList.addAll(posesNew);
            posesNew = new ArrayList();
            ++i;
        }
        List<Block> legitBlocks = Arrays.asList(Blocks.water, Blocks.lava, Blocks.flowing_lava, Blocks.air, Blocks.flowing_water, Blocks.fire);
        return finalList.stream().anyMatch(blockPos -> legitBlocks.contains(Xray.mc.theWorld.getBlockState(blockPos).getBlock()));
    }

    public boolean test(BlockPos pos1) {
        if (!this.isTarget(pos1)) {
            return false;
        }
        if (!this.oreTest(pos1, depth.getCurrent()).booleanValue()) {
            return false;
        }
        if (radiusOn.getEnable()) {
            return !(Xray.mc.thePlayer.getDistance((double)pos1.func_177958_n(), (double)pos1.func_177956_o(), (double)pos1.func_177952_p()) >= radius.getCurrent());
        }
        return true;
    }

    public boolean isTarget(BlockPos pos) {
        Block block = Xray.mc.theWorld.getBlockState(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return diammond.getEnable();
        }
        if (Blocks.lapis_ore.equals(block)) {
            return lapis.getEnable();
        }
        if (Blocks.iron_ore.equals(block)) {
            return iron.getEnable();
        }
        if (Blocks.gold_ore.equals(block)) {
            return gold.getEnable();
        }
        if (Blocks.coal_ore.equals(block)) {
            return coal.getEnable();
        }
        if (Blocks.emerald_ore.equals(block)) {
            return emerald.getEnable();
        }
        if (Blocks.redstone_ore.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return redstone.getEnable();
        }
        return false;
    }
}

