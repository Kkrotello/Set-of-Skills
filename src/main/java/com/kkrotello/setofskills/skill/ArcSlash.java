package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.capabilities.PlayerStatProvider;
import com.kkrotello.setofskills.entity.SlashProjectileEntity;
import com.kkrotello.setofskills.network.ModMessages;
import com.kkrotello.setofskills.network.PlayerStatSyncS2C;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class ArcSlash {
    public static void execute(Level world, Player user, double x, double y, double z){
        {
            user.getCapability(PlayerStatProvider.PlAYER_STATS).ifPresent(playerStats -> {
                playerStats.PlusStrength(1);
                if (user instanceof ServerPlayer sPLayer) {
                    ModMessages.sendToPlayer(new PlayerStatSyncS2C(playerStats.strength), sPLayer);
                }
            });

            Entity shootFrom = user;
            if (shootFrom == null){
                return;
            }
            Level projectileLevel = shootFrom.level();
            if (!projectileLevel.isClientSide()) {
                SlashProjectileEntity Slash =  new SlashProjectileEntity(projectileLevel);
                Slash.setPos(shootFrom.getX(), shootFrom.getEyeY() - 0.1, shootFrom.getZ());
//                Slash.setYRot(90f);
//                Slash.shootFromRotation(shootFrom, shootFrom.getXRot(), shootFrom.getYRot(), 0.0f, 1.0f, 0.0f);
//                Slash.shoot(shootFrom.getLookAngle().x, shootFrom.getLookAngle().y, shootFrom.getLookAngle().z, 1, 0);
                Slash.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1f, 0.0F);
//                Slash.getPersistentData().putString("NoGravity", "1b");
                projectileLevel.addFreshEntity(Slash);
            }
        }

    }
}
