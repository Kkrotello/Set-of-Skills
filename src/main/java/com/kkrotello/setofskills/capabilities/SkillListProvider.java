package com.kkrotello.setofskills.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkillListProvider implements ICapabilityProvider, INBTSerializable<ListTag> {

    public static Capability<SkillList> SKILL_LIST = CapabilityManager.get(new CapabilityToken<SkillList>() { });
    private final LazyOptional<SkillList> optional = LazyOptional.of(this::createPlayerSkills);
    public SkillList skills = null;
    public static SkillList fake = new SkillList();
    private SkillList createPlayerSkills() {
        if(this.skills == null) {
            this.skills = new SkillList();
        }

        return this.skills;
    }

    public static SkillList getCap(Player player) {
//        if (le == null) return OHNO;
        return player.getCapability(SKILL_LIST).orElse(fake);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == SKILL_LIST) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }


    @Override
    public ListTag serializeNBT() {
        ListTag nbt = new ListTag();
        createPlayerSkills().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(ListTag nbt) {
        createPlayerSkills().loadNBTData(nbt);
    }
}
