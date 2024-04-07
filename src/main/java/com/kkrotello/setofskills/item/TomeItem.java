package com.kkrotello.setofskills.item;

import com.kkrotello.setofskills.capabilities.SkillListProvider;
import com.kkrotello.setofskills.client.SkillCollectionMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

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
        pPlayer.displayClientMessage(Component.literal("tome opened"),true);

        FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
        packetBuffer.writeBlockPos(pPlayer.blockPosition());
        packetBuffer.writeByte(pUsedHand == InteractionHand.MAIN_HAND ? 0 : 1);
        if(pPlayer.isShiftKeyDown()){
            pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
                return ChestMenu.threeRows(p_53124_, p_53125_, playerskills);
            }, Component.literal("Tome of Skills")));
        } else{
            if (!pLevel.isClientSide()) {

//                NetworkHooks.openScreen((ServerPlayer) pPlayer,(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//                    return new SkillCollectionMenu(p_53124_, p_53125_, playerskills);
//                }, Component.literal("Tome of Skills"))));
                NetworkHooks.openScreen((ServerPlayer) pPlayer, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Tome");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new SkillCollectionMenu(id, inventory, playerskills);
                }
                });
            }


//            NetworkHooks.openScreen((ServerPlayer) pPlayer, new MenuProvider() {
//                @Override
//                public Component getDisplayName() {
//                    return Component.literal("Tome");
//                }
//
//                @Override
//                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
//                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
//                    packetBuffer.writeBlockPos(pPlayer.blockPosition());
//                    packetBuffer.writeByte(pUsedHand == InteractionHand.MAIN_HAND ? 0 : 1);
//                    return new SkillCollectionMenu(id, inventory, packetBuffer);
//                }
//            }, buf -> {
//                buf.writeBlockPos(pPlayer.blockPosition());
//                buf.writeByte(pUsedHand == InteractionHand.MAIN_HAND ? 0 : 1);
//            });
//        }



        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}