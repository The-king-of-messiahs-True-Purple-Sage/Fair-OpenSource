/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package com.Fair.GhostClient.modules;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.Utils.Connection;
import com.Fair.GhostClient.Utils.TimerUtils;
import com.Fair.GhostClient.modules.Category;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public boolean state = false;
    public int key;
    public int Y;
    public String name;
    public Category category;
    public int color = -1;
    public boolean als;
    public ArrayList<Setting> setting;
    public String description;
    private TimerUtils timerUtils = new TimerUtils();

    public int getY() {
        return this.Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public Module(String name, int key, Category category, boolean als, String description) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.als = als;
        this.description = description;
        this.setting = new ArrayList();
        if (this.als) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            FMLCommonHandler.instance().bus().register((Object)this);
        }
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<Setting>();
        for (Setting s : this.getSettings()) {
            if (!s.getParentMod().equals(mod)) continue;
            out.add(s);
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module getModuleBySetting(Setting setting) {
        for (Module module : Client.moduleManager.getModules()) {
            if (module.getSetting() != setting) continue;
            return module;
        }
        return null;
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        return true;
    }

    public List<Setting> getSetting() {
        return this.setting;
    }

    public ArrayList<Setting> getSettings() {
        return this.setting;
    }

    public void isSilder() {
    }

    public void setMode() {
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void gtoggle() {
        this.setState(!this.state, true);
    }

    public void setEnable() {
        this.setState(true, true);
    }

    public void toggle() {
        this.setState(!this.state, false);
    }

    public void setState(boolean state, boolean g) {
        if (this.name == "Hud") {
            return;
        }
        if (this.key == 0 && !g) {
            return;
        }
        if (this.state == state) {
            return;
        }
        if (this.timerUtils.isDelayComplete(10.0)) {
            this.state = state;
            System.out.println(this.state);
            if (state) {
                MinecraftForge.EVENT_BUS.register((Object)this);
                FMLCommonHandler.instance().bus().register((Object)this);
                this.enable();
            } else {
                MinecraftForge.EVENT_BUS.unregister((Object)this);
                FMLCommonHandler.instance().bus().unregister((Object)this);
                this.disable();
            }
            this.timerUtils.reset();
        }
    }

    public void enable() {
    }

    public void disable() {
    }

    public int getKeybind() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public int getKey() {
        return this.key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return this.state;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

