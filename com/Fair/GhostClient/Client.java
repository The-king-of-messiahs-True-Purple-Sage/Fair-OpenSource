/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package com.Fair.GhostClient;

import com.Fair.GhostClient.Event.TestEvent;
import com.Fair.GhostClient.Event.eventhandlers.EventsHandler;
import com.Fair.GhostClient.Utils.CFontRenderer;
import com.Fair.GhostClient.Utils.FontLoaders;
import com.Fair.GhostClient.Utils.Nan0EventRegister;
import com.Fair.GhostClient.gui.clickgui.CategoryPanel;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.ModuleManager;
import com.Fair.GhostClient.modules.Tools;
import java.awt.Color;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Client {
    public static Client instance;
    public static boolean state;
    public static ModuleManager moduleManager;
    public static int x;
    public static int y;
    public static boolean isinited;
    public static boolean bin;
    public static boolean isObfuscate;
    public static String choose;
    private boolean initialized = false;
    public Module module;
    public static String hovca;
    public int i = 1;
    public static CFontRenderer cFontRenderer;
    public static EventsHandler eventsHandler;

    public Client() {
        try {
            File FontDir;
            moduleManager = new ModuleManager();
            eventsHandler = new EventsHandler();
            Nan0EventRegister.register(MinecraftForge.EVENT_BUS, eventsHandler);
            Nan0EventRegister.register(FMLCommonHandler.instance().bus(), eventsHandler);
            MinecraftForge.EVENT_BUS.register((Object)this);
            MinecraftForge.EVENT_BUS.register((Object)TestEvent.INSTANCE);
            FMLCommonHandler.instance().bus().register((Object)this);
            File ConfigDir = new File(Tools.getConfigPath());
            if (ConfigDir.exists()) {
                ConfigDir.mkdir();
            }
            if ((FontDir = new File(Tools.getFontPath())).exists()) {
                FontDir.mkdir();
            }
            CategoryPanel.color = new Color(0, 255, 148).getRGB();
            CategoryPanel.colors = new Color(0, 255, 148);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Tools.nullCheck()) {
            this.initialized = false;
            return;
        }
        try {
            if (!this.initialized) {
                this.initialized = true;
            }
            Module Reach2 = moduleManager.getModule("Reach");
            Module HitBox2 = moduleManager.getModule("HitBox");
            Module autoclicker = moduleManager.getModule("AutoClicker");
            Minecraft.getMinecraft().entityRenderer.getMouseOver(1.0f);
            Tools.su();
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        try {
            for (Module m : moduleManager.getModules()) {
                if (!Keyboard.isKeyDown((int)m.key)) continue;
                m.toggle();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static {
        state = false;
        isinited = false;
        bin = false;
        isObfuscate = false;
        cFontRenderer = new CFontRenderer(FontLoaders.getFont(16), true, true);
    }
}

