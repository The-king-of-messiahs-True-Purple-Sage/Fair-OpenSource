/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package com.Fair.GhostClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
    public static void component(ChatComponentText component) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().ingameGUI.getChatGUI() == null) {
            return;
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("").appendSibling((IChatComponent)component));
    }

    public static void message(Object message) {
        ChatUtils.component(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "[Fair]\u00a77" + message));
    }

    public static void report(String message) {
        ChatUtils.message(EnumChatFormatting.GREEN + message);
    }

    public static void warning(Object message) {
        ChatUtils.message("\u00a78[\u00a7eWARNING\u00a78]\u00a7e " + message);
    }

    public static void error(Object message) {
        ChatUtils.message("\u00a78[\u00a74ERROR\u00a78]\u00a7c " + message);
    }
}

