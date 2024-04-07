package com.kkrotello.setofskills.client;

import com.kkrotello.setofskills.capabilities.SkillListProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SkillCollectionMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

    public final static HashMap<String, Object> guistate = new HashMap<>();
    public Player user = null;
    public Level world = null;
    public int x, y, z;
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;
    private final Map<Integer, Slot> customSlots = new HashMap<>();



    public SkillCollectionMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenuTypes.SKILL_COllECTION.get(), id);
        this.user = inv.player;
        this.world = inv.player.level();
//        this.internal = new ItemStackHandler(3);
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
//            access = ContainerLevelAccess.create(world, pos);
        }
//        if (pos != null) {
//            if (extraData.readableBytes() == 1) { // bound to item
//                byte hand = extraData.readByte();
//                ItemStack itemstack = hand == 0 ? this.user.getMainHandItem() : this.user.getOffhandItem();
//                this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
//                itemstack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
//                    this.internal = capability;
//                    this.bound = true;
//                });
//            } else if (extraData.readableBytes() > 1) { // bound to entity
//                extraData.readByte(); // drop padding
//                boundEntity = world.getEntity(extraData.readVarInt());
//                if (boundEntity != null)
//                    boundEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
//                        this.internal = capability;
//                        this.bound = true;
//                    });
//            } else { // might be bound to block
//                boundBlockEntity = this.world.getBlockEntity(pos);
//                if (boundBlockEntity != null)
//                    boundBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
//                        this.internal = capability;
//                        this.bound = true;
//                    });
//            }
//        }

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        inv.player.getCapability(SkillListProvider.SKILL_LIST).ifPresent(skillList -> {
            IItemHandler items = (IItemHandler) skillList;
            this.addSlot(new SlotItemHandler(items, 0, 114, 13) {
                private final int slot = 0;
            });
            this.addSlot(new SlotItemHandler(items, 1, 95, 39) {
                private final int slot = 1;

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(items, 2, 132, 39) {
                private final int slot = 2;

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        });

//        for (int si = 0; si < 3; ++si)
//            for (int sj = 0; sj < 9; ++sj)
//                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
//        for (int si = 0; si < 9; ++si)
//            this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
    }

    public SkillCollectionMenu (int pContainerId, Inventory pPlayerInventory, Container pContainer){
        super(ModMenuTypes.SKILL_COllECTION.get(), pContainerId);
        IItemHandler contitems = new ItemStackHandler(3);
        checkContainerSize(pContainer, 3);
        pContainer.startOpen(pPlayerInventory.player);
        this.addSlot(new Slot(pContainer,1, 114, 13));
        this.addSlot(new Slot(pContainer, 2, 95, 39));
        this.addSlot(new Slot(pContainer, 3, 132, 39));
    }

    private void addPlayerHotbar(Inventory inv) {
        return;
    }

    private void addPlayerInventory(Inventory inv) {
        return;
    }


    @Override
    public Map<Integer, Slot> get() {
        return null;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}


