package com.kkrotello.setofskills.client;

import com.kkrotello.setofskills.capabilities.SkillList;
import com.kkrotello.setofskills.capabilities.SkillListProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SkillBeta extends AbstractContainerMenu {

    public final static HashMap<String, Object> guistate = new HashMap<>();
    public Player user = null;
    public Level world = null;
    public int x, y, z;
    public SimpleContainer Skills;

    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;
    private final Map<Integer, Slot> customSlots = new HashMap<>();

    protected SkillBeta(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    public SkillBeta(int i, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenuTypes.SBETA.get(), i);
        this.user = inv.player;
        this.world = inv.player.level();
//        this.internal = new ItemStackHandler(3);
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        }
        inv.player.getCapability(SkillListProvider.SKILL_LIST).ifPresent(skillList -> {
            Container skillitems = skillList.getSkills();
            Skills = skillList.getSkills();
            this.addSlot(new Slot(skillitems, 0, 114, 13) {
                private final int slot = 0;
            });
            this.addSlot(new Slot(skillitems, 1, 95, 39) {
                private final int slot = 1;
            });
            this.addSlot(new Slot(skillitems, 2, 132, 39) {
                private final int slot = 2;

                @Override
                public boolean mayPickup(Player pPlayer) {
                    return false;
                }
            });
            this.addSlot(new Slot(skillitems, 3, 114, 10000) {
                private final int slot = 3;
            });
        });
    }

    public SkillBeta (int pContainerId, Inventory pPlayerInventory, Container pContainer){
        super(ModMenuTypes.SBETA.get(), pContainerId);
        IItemHandler contitems = new ItemStackHandler(3);
        pPlayerInventory.player.getCapability(SkillListProvider.SKILL_LIST).ifPresent(skillList -> {
            Skills = skillList.getSkills();
        });
//        checkContainerSize(pContainer, 3);
        pContainer.startOpen(pPlayerInventory.player);
        this.addSlot(new Slot(pContainer,0, 114, 13));
        this.addSlot(new Slot(pContainer, 1, 95, 39));
        this.addSlot(new Slot(pContainer, 2, 132, 39));
        this.addSlot(new Slot(pContainer, 3, 114, 10000));
    }
    public int getCount(){
        int i;
        int count = 0;
        for(i = 0; i < 27; i++){
            ItemStack item = Skills.getItem(i);
            if(item != ItemStack.EMPTY){
                count++;
            }
        }
        return count;
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
