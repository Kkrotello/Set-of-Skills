package com.kkrotello.setofskills.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class SkillList{

    public SimpleContainer skills = new SimpleContainer(27);

    public void saveNBTData(ListTag nbt) {
        for(int $$1 = 0; $$1 < this.skills.getContainerSize(); ++$$1) {
            ItemStack item = this.skills.getItem($$1);
            if (!item.isEmpty()) {
                CompoundTag $$3 = new CompoundTag();
                $$3.putByte("Slot", (byte)$$1);
                item.save($$3);
                nbt.add($$3);
            }
        }
    }

    public void loadNBTData(ListTag nbt) {
        int $$2;
        for($$2 = 0; $$2 < this.skills.getContainerSize(); ++$$2) {
            this.skills.setItem($$2, ItemStack.EMPTY);
        }

        for($$2 = 0; $$2 < nbt.size(); ++$$2) {
            CompoundTag tagitem = nbt.getCompound($$2);
            int index = tagitem.getByte("Slot") & 255;
            if (index >= 0 && index < this.skills.getContainerSize()) {
                this.skills.setItem(index, ItemStack.of(tagitem));
            }
        }
    }
    public void copyFrom(SkillList source) {
        int $$2;
        for($$2 = 0; $$2 < this.skills.getContainerSize(); ++$$2) {
            ItemStack copyitem = source.skills.getItem($$2);
            if ($$2 >= 0 && $$2 < this.skills.getContainerSize()) {
                this.skills.setItem($$2, copyitem);
            }
        }
    }

    public SimpleContainer getSkills() {
        return skills;
    }
}
