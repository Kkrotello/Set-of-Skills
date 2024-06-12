package com.kkrotello.setofskills.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerStats {
    public int strength;

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("strength", strength);
    }

    public void loadNBTData(CompoundTag nbt) {
        strength = nbt.getInt("strength");
    }
    public void copyFrom(PlayerStats source) {
        this.strength = source.strength;
    }

    public void PlusStrength(int i) {
        this.strength += 1;
    }
}
