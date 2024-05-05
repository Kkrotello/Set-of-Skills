package com.kkrotello.setofskills.client;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SetOfSkills.MODID);

    public static final RegistryObject<MenuType<SkillCollectionMenu>> SKILL_COllECTION
            = MENUS.register("skill_collection", () -> IForgeMenuType.create(SkillCollectionMenu::new));
    public static final RegistryObject<MenuType<TestMenu>> TEST
            = MENUS.register("test", () -> IForgeMenuType.create(TestMenu::new));

    public static final RegistryObject<MenuType<SkillBeta>> SBETA
            = MENUS.register("sbeta", () -> IForgeMenuType.create(SkillBeta::new));


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
