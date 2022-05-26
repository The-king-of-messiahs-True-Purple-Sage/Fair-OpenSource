/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package com.Fair.GhostClient.Event.eventhandlers;

import com.Fair.GhostClient.Client;
import com.Fair.GhostClient.Event.EventManager;
import com.Fair.GhostClient.Utils.Connection;
import com.Fair.GhostClient.Utils.HUDUtils;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventsHandler {
    private static long lastMS = 0L;
    private boolean initialized = false;
    private long lastFrame;
    private int tips;
    public static String UnSuppotIPs = "join.mchycraft.com:25565";

    public EventsHandler() {
        EventManager.register(this);
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Module hack : Client.moduleManager.getModules()) {
            if (!hack.getState() || Minecraft.getMinecraft().theWorld == null) continue;
            suc &= hack.onPacket(packet, side);
        }
        return suc;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Tools.nullCheck()) {
            this.initialized = false;
            return;
        }
        try {
            if (!this.initialized) {
                new Connection(this);
                this.initialized = true;
            }
            long currentTimeMillis = System.currentTimeMillis();
            HUDUtils.delta = (float)(currentTimeMillis - this.lastFrame) / 500.0f;
            this.lastFrame = currentTimeMillis;
            Module Reach2 = Client.moduleManager.getModule("Reach");
            Module HitBox2 = Client.moduleManager.getModule("HitBox");
            Module autoclicker = Client.moduleManager.getModule("AutoClicker");
            Minecraft.getMinecraft().entityRenderer.getMouseOver(1.0f);
            Tools.su();
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        for (Module m : Client.moduleManager.getModules()) {
            if (Keyboard.isKeyDown((int)0) || !Keyboard.isKeyDown((int)m.key)) continue;
            m.toggle();
        }
    }

    public static String getCpuId() throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        System.out.println(property + ": " + serial);
        return serial;
    }

    public static String Send(String IP, int Port, String Message) {
        try {
            Socket socket = new Socket(IP, Port);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops, "GBK");
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Message);
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips, "GBK");
            BufferedReader br = new BufferedReader(ipsr);
            String s = null;
            s = br.readLine();
            if (s != null) {
                return s;
            }
            socket.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed Connect to The Server(0x66FF)", "ClassLoader", 0);
            e.printStackTrace();
        }
        return null;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public void setLastMS() {
        lastMS = System.currentTimeMillis();
    }
}

