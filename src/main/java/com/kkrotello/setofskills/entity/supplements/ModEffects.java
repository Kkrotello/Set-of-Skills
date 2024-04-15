package com.kkrotello.setofskills.entity.supplements;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {
    public static final DeferredRegister<MobEffect> MODEFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SetOfSkills.MODID);

    public static final RegistryObject<MobEffect> HALT = MODEFFECTS.register("halt", ()-> new HaltEffect(MobEffectCategory.NEUTRAL, 1679)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, AttributeModifier.Operation.MULTIPLY_TOTAL));


    public static void register(IEventBus eventBus) {
        MODEFFECTS.register(eventBus);
    }
}
