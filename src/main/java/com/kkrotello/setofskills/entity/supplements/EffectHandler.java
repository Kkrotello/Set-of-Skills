package com.kkrotello.setofskills.entity.supplements;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectHandler {


    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent ev) {
        Entity entity = ev.getEntity();
        HaltEffect.LivingStun(entity.level(), entity);
    }

    @SubscribeEvent
    public static void onEntityAttack (LivingAttackEvent ev) {
        Entity target = ev.getEntity();
        DamageSource attack = ev.getSource();
        Entity attacker = attack.getEntity();
        if(attacker instanceof LivingEntity lA){
            if(lA.hasEffect(ModEffects.HALT.get())){
                ev.setCanceled(true);
            }
        }
    }
}
