package com.kkrotello.setofskills.item;

import com.kkrotello.setofskills.capabilities.SkillListProvider;
import com.kkrotello.setofskills.client.SkillBeta;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SColllectItem extends Item {
    public SColllectItem(Item.Properties properties) {
        super(properties);
    }

    public Container playerskills;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCapability(SkillListProvider.SKILL_LIST).ifPresent(skillList -> {
            playerskills = skillList.getSkills();
        });
        pPlayer.displayClientMessage(Component.literal("Open"),true);
        pPlayer.openMenu(new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> {
            return new SkillBeta(p_57074_, p_57075_, playerskills);
            }, Component.literal("works")));

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
