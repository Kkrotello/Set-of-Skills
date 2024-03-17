package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.util.RayTrace;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DropShotKick {
    public static void execute(Level world, Player user) {
        LivingEntity target = RayTrace.Raytracetarget(world, user, range);
        if (target == null)
            return;
        user.setDeltaMovement(user.getDeltaMovement()
                .add(user.position().vectorTo(target.position()).scale(0.5)));
        target.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 5);

    }
    public static int range = 5;
}
