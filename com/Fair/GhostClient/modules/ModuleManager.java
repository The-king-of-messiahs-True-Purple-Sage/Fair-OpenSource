/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.modules;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Setting;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.combat.AimAssist;
import com.Fair.GhostClient.modules.combat.AutoBlock;
import com.Fair.GhostClient.modules.combat.AutoClicker;
import com.Fair.GhostClient.modules.combat.ChestStealer;
import com.Fair.GhostClient.modules.combat.Criticals;
import com.Fair.GhostClient.modules.combat.HitBox;
import com.Fair.GhostClient.modules.combat.Killaura;
import com.Fair.GhostClient.modules.combat.Reach;
import com.Fair.GhostClient.modules.combat.Velocity;
import com.Fair.GhostClient.modules.configs.ReloadConfig;
import com.Fair.GhostClient.modules.configs.SaveConfig;
import com.Fair.GhostClient.modules.misc.AntiAFK;
import com.Fair.GhostClient.modules.misc.MLG;
import com.Fair.GhostClient.modules.misc.SafeWalk;
import com.Fair.GhostClient.modules.movement.Fly;
import com.Fair.GhostClient.modules.movement.InvMove;
import com.Fair.GhostClient.modules.movement.KeepSprint;
import com.Fair.GhostClient.modules.movement.NoSlowDown;
import com.Fair.GhostClient.modules.movement.Sprint;
import com.Fair.GhostClient.modules.render.Chams;
import com.Fair.GhostClient.modules.render.ChestESP;
import com.Fair.GhostClient.modules.render.Click;
import com.Fair.GhostClient.modules.render.ESP;
import com.Fair.GhostClient.modules.render.FullBright;
import com.Fair.GhostClient.modules.render.Hud;
import com.Fair.GhostClient.modules.render.ItemESP;
import com.Fair.GhostClient.modules.render.Nametags;
import com.Fair.GhostClient.modules.render.NoBob;
import com.Fair.GhostClient.modules.render.Tracers;
import com.Fair.GhostClient.modules.render.Xray;
import com.Fair.GhostClient.modules.world.AutoArmor;
import com.Fair.GhostClient.modules.world.AutoTool;
import com.Fair.GhostClient.modules.world.BuildReach;
import com.Fair.GhostClient.modules.world.FastPlace;
import com.Fair.GhostClient.modules.world.InvCleaner;
import com.Fair.GhostClient.modules.world.Scaffold;
import com.Fair.GhostClient.modules.world.Timer;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    static ArrayList<Module> list = new ArrayList();
    public static volatile ModuleManager INSTANCE = new ModuleManager();

    public ArrayList<Module> getModuleByType(Category category) {
        ArrayList<Module> modules = new ArrayList<Module>();
        for (Module it : this.getModules()) {
            if (!it.getCategory().equals((Object)category)) continue;
            modules.add(it);
        }
        return modules;
    }

    public ArrayList<Module> getModuleBySetting(Setting setting) {
        ArrayList<Module> modules = new ArrayList<Module>();
        for (Module module : Client.moduleManager.getModules()) {
            if (!module.getSetting().equals(setting)) continue;
            modules.add(module);
        }
        return modules;
    }

    public ArrayList<Module> getModules() {
        return list;
    }

    public final List<Module> getModulesForCategory(Category category) {
        ArrayList<Module> localModules = new ArrayList<Module>();
        ArrayList<Module> modules = list;
        int modulesSize = modules.size();
        for (int i = 0; i < modulesSize; ++i) {
            Module module = modules.get(i);
            if (module.getCategory() != category) continue;
            localModules.add(module);
        }
        return localModules;
    }

    public Module getModule(String name) {
        for (Module m : list) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    static {
        System.out.println("EZ Cracked By \u7d2b\u5723\u771f\u5c0a \u82b1\u8d3910s \u5b8c\u5168Dump \u987a\u4fbf\u5e2e\u4f60\u4fee\u4e86\u4e0bClickgui");
        list.add(new Killaura());
        list.add(new AutoClicker());
        list.add(new AimAssist());
        list.add(new Sprint());
        list.add(new Hud());
        list.add(new Reach());
        list.add(new Velocity());
        list.add(new FullBright());
        list.add(new FastPlace());
        list.add(new NoSlowDown());
        list.add(new HitBox());
        list.add(new Nametags());
        list.add(new MLG());
        list.add(new AutoTool());
        list.add(new SafeWalk());
        list.add(new Criticals());
        list.add(new Scaffold());
        list.add(new Fly());
        list.add(new Xray());
        list.add(new KeepSprint());
        list.add(new Timer());
        list.add(new ChestESP());
        list.add(new Tracers());
        list.add(new ESP());
        list.add(new ChestStealer());
        list.add(new InvMove());
        list.add(new Chams());
        list.add(new Click());
        list.add(new AutoBlock());
        list.add(new BuildReach());
        list.add(new AutoArmor());
        list.add(new InvCleaner());
        list.add(new AntiAFK());
        list.add(new ItemESP());
        list.add(new SaveConfig());
        list.add(new ReloadConfig());
        list.add(new NoBob());
    }
}

