/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package com.Fair.GhostClient.Utils;

import net.minecraft.util.BlockPos;

public class nBlockPos
extends BlockPos {
    private int x;
    private int y;
    private int z;

    public nBlockPos() {
        super(0, 0, 0);
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int func_177958_n() {
        return this.x;
    }

    public int func_177956_o() {
        return this.y;
    }

    public int func_177952_p() {
        return this.z;
    }
}

