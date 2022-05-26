/*
 * Decompiled with CFR 0.152.
 */
package com.Fair.GhostClient.modules.configs;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Utils.FontLoaders;
import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;

public class UpdateGUI
extends Module {
    public UpdateGUI() {
        super("UpdateGUI", 0, Category.Configs, false, "");
    }

    @Override
    public void enable() {
        super.enable();
        this.gtoggle();
        Client.cFontRenderer = FontLoaders.F16;
    }
}

