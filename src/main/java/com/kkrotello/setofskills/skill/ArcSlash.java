package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.entity.SlashProjectileEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class ArcSlash {
    public static void execute(Level world, Player user, double x, double y, double z){
        {
            Entity shootFrom = user;
            if (shootFrom == null){
                return;
            }
            Level projectileLevel = shootFrom.level();
            if (!projectileLevel.isClientSide()) {
                Projectile Slash = new Object() {
                    public Projectile getSlash(Level level, double ax, double ay, double az) {
                        AbstractHurtingProjectile entityToSpawn = new SlashProjectileEntity(level);
                        entityToSpawn.xPower = ax;
                        entityToSpawn.yPower = ay;
                        entityToSpawn.zPower = az;
                        return entityToSpawn;
                    }
                }.getSlash(projectileLevel, 0.5, 0.5, 0.5);
                Slash.setPos(shootFrom.getX(), shootFrom.getEyeY() - 0.1, shootFrom.getZ());
                Slash.shoot(shootFrom.getLookAngle().x, shootFrom.getLookAngle().y, shootFrom.getLookAngle().z, 1, 0);
                projectileLevel.addFreshEntity(Slash);
            }
        }

    }
}
