package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.capabilities.PlayerStatProvider;
import com.kkrotello.setofskills.client.ClientStatData;
import com.kkrotello.setofskills.util.RayTrace;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DropShotKick {
    public static void execute(Level world, Player user) {


        if (world.isClientSide) {
            user.getCapability(PlayerStatProvider.PlAYER_STATS).ifPresent(playerStats -> {
                user.sendSystemMessage(Component.literal("Current Thirst " + playerStats.strength)
                        .withStyle(ChatFormatting.AQUA));
            });
            user.sendSystemMessage(Component.literal(String.valueOf(ClientStatData.getStrength())).withStyle(ChatFormatting.LIGHT_PURPLE));
        } else{
            user.getCapability(PlayerStatProvider.PlAYER_STATS).ifPresent(playerStats -> {
                user.sendSystemMessage(Component.literal("Current Thirst " + playerStats.strength)
                        .withStyle(ChatFormatting.GOLD));
            });
        }


        LivingEntity target = RayTrace.Raytracetarget(world, user, range);
        if (target == null)
            return;
        user.setDeltaMovement(user.getDeltaMovement()
                .add(user.position().vectorTo(target.position()).scale(0.5)));
        target.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 5);

    }
    public static int range = 5;
}
