package com.kkrotello.setofskills.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStatProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerStats> PlAYER_STATS = CapabilityManager.get(new CapabilityToken<PlayerStats>() { });
    private final LazyOptional<PlayerStats> optional = LazyOptional.of(this::createPlayerStats);
    public PlayerStats Stats = null;
    public static PlayerStats fake = new PlayerStats();
    private PlayerStats createPlayerStats() {
        if(this.Stats == null) {
            this.Stats = new PlayerStats();
        }

        return this.Stats;
    }
    public static PlayerStats getCap(Player player) {
//        if (le == null) return OHNO;
        return player.getCapability(PlAYER_STATS).orElse(fake);
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == PlAYER_STATS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStats().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStats().loadNBTData(nbt);
    }
}
