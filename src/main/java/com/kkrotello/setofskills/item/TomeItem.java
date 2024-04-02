package com.kkrotello.setofskills.item;

import com.kkrotello.setofskills.capabilities.SkillListProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TomeItem extends Item {
    public TomeItem(Properties pProperties) {
        super(pProperties);
    }

    SimpleContainer playerskills;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCapability(SkillListProvider.SKILL_LIST).ifPresent(skillList -> {
            playerskills = skillList.getSkills();
        });
//        PlayerEnderChestContainer ender = pPlayer.getEnderChestInventory();
        pPlayer.displayClientMessage(Component.literal("tome opened"),true);

            pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
                return ChestMenu.threeRows(p_53124_, p_53125_, playerskills);
            }, Component.literal("Tome of Skills")));

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
