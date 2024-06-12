package com.kkrotello.setofskills.client;

import com.kkrotello.setofskills.capabilities.PlayerStats;
import net.minecraft.nbt.CompoundTag;

public class ClientStatData {
    public static int strength;

    public static void copyFrom(int source) {
        strength = source;
    }

    public static int getStrength(){
        return strength;
    }
}
