/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Keyboard
 */
package com.Fair.GhostClient.modules.movement;

import com.Fair.GhostClient.modules.Category;
import com.Fair.GhostClient.modules.Module;
import com.Fair.GhostClient.modules.Tools;
import com.Fair.GhostClient.settings.KeyBindSetting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class InvMove
extends Module {
    private KeyBindSetting KeyBind = new KeyBindSetting("KeyBind", 0, this);

    public InvMove() {
        super("InvMove", 0, Category.Movement, false, "");
        this.getSetting().add(this.KeyBind);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (InvMove.mc.currentScreen != null) {
            if (!(InvMove.mc.currentScreen instanceof GuiChat)) {
                KeyBinding[] key;
                KeyBinding[] keyBindingArray = new KeyBinding[6];
                keyBindingArray[0] = InvMove.mc.gameSettings.keyBindForward;
                keyBindingArray[1] = InvMove.mc.gameSettings.keyBindBack;
                keyBindingArray[2] = InvMove.mc.gameSettings.keyBindLeft;
                keyBindingArray[3] = InvMove.mc.gameSettings.keyBindRight;
                keyBindingArray[4] = InvMove.mc.gameSettings.keyBindSprint;
                keyBindingArray[5] = InvMove.mc.gameSettings.keyBindJump;
                KeyBinding[] array = key = keyBindingArray;
                int lengths = key.length;
                for (int i = 0; i < lengths; ++i) {
                    KeyBinding b = array[i];
                    KeyBinding.setKeyBindState((int)b.getKeyCode(), (boolean)Keyboard.isKeyDown((int)b.getKeyCode()));
                }
            }
        }
    }
}

