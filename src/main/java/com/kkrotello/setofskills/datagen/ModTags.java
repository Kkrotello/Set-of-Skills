package com.kkrotello.setofskills.datagen;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(SetOfSkills.MODID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SKILL_EMBLEMS = tag("skill_emblems");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SetOfSkills.MODID, name));
        }
    }
}
