package com.kkrotello.setofskills.entity;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SetOfSkills.MODID);
    public static final RegistryObject<EntityType<SlashProjectileEntity>> SLASH_PROJECTILE =
            ENTITY_TYPES.register("slash_projectile", ()-> EntityType.Builder.<SlashProjectileEntity>of
                    (SlashProjectileEntity::new, MobCategory.MISC).sized(1.3f,0.8f).build("slash_projectile"));
    public static final RegistryObject<EntityType<RicochetArrowEntity>> RICHOCHET_ARROW =
            ENTITY_TYPES.register("richochet_arrow", ()-> EntityType.Builder.<RicochetArrowEntity>of
                    (RicochetArrowEntity::new, MobCategory.MISC).sized(0.6f,0.6f).build("slash_projectile"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
