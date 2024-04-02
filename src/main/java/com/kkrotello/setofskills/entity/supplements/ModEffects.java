package com.kkrotello.setofskills.entity.supplements;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {
    public static final DeferredRegister<MobEffect> MODEFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SetOfSkills.MODID);

    public static final RegistryObject<MobEffect> HALT = MODEFFECTS.register("halt", ()-> new HaltEffect(MobEffectCategory.NEUTRAL, 1679));


    public static void register(IEventBus eventBus) {
        MODEFFECTS.register(eventBus);
    }
}
