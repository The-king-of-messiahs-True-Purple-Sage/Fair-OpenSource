/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package com.Fair.GhostClient;

import com.Fair.GhostClient.Client;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="Fair", name="Client", version="1.0.0", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {
    @Mod.EventHandler
    public void Mod(FMLPreInitializationEvent event) {
        new Client();
    }
}

