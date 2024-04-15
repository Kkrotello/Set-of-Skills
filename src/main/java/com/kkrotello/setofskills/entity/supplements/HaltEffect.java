package com.kkrotello.setofskills.entity.supplements;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class HaltEffect extends MobEffect {
    protected HaltEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    private static final Map<LivingEntity, Vec3> stunned = new HashMap<LivingEntity, Vec3>();
    private static final Map<LivingEntity, Float> neckstunx = new HashMap<LivingEntity, Float>();
    private static final Map<LivingEntity, Float> neckstuny = new HashMap<LivingEntity, Float>();

    @Override
    public String getDescriptionId() {
        return "effect.setofskills.halt_effect";
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        Level world = entity.level();
        if(entity instanceof Mob mob){
            mob.setNoAi(false);
        }
//        entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 5);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        stunned.put(pLivingEntity, pLivingEntity.position());
        neckstunx.put(pLivingEntity, pLivingEntity.getXRot());
        neckstuny.put(pLivingEntity, pLivingEntity.getYHeadRot());
        if(pLivingEntity instanceof Mob mob){
            mob.setNoAi(true);
        }
    }

    public static void LivingStun(Level world, Entity ent){
        if (world.isClientSide) {
            return;
        }

        if(!(ent instanceof LivingEntity)) {
            return;
        }

        LivingEntity lent = (LivingEntity)ent;

        if (lent.getEffect(ModEffects.HALT.get()) != null) {
            if (stunned.containsKey(lent)) {
                Vec3 lastvec = stunned.get(lent);
                float lastx = neckstunx.get(lent);
                float lasty = neckstuny.get(lent);
                lent.teleportTo(lastvec.x, lent.getY(), lastvec.z);
                lent.setXRot(lastx);
                lent.setYHeadRot(lasty);
            }
        }
        else if (stunned.containsKey(lent)) {
            stunned.remove(lent);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
