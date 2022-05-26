/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.modules.world;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.IntegerSetting;
import com.Fair.GhostClient.settings.KeyBindSetting;

public class BuildReach
extends Module {
    private IntegerSetting maxRange = new IntegerSetting("Build Range", 3.0, 3.0, 10.0);
    public static boolean isBuildReach = false;
    public static float BuildDistance = 3.0f;
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public BuildReach() {
        super("BuildReach", 0, Category.World, false, "");
        this.getSetting().add(this.maxRange);
        this.getSetting().add(this.KeyBind);
    }

    @Override
    public void enable() {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        BuildDistance = (float)this.maxRange.getCurrent();
        isBuildReach = true;
        super.enable();
    }

    @Override
    public void disable() {
        BuildDistance = 3.0f;
        isBuildReach = false;
        super.disable();
    }
}

