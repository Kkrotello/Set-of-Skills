package com.kkrotello.setofskills.util;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class RayTrace {
    public static LivingEntity Raytracetarget(Level world, LivingEntity caster, double range){
        Vec3 start = caster.getEyePosition();
        Vec3 look = caster.getLookAngle().scale(range + 2);
        Vec3 end = start.add(look);
        LivingEntity entity = null;
        List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().expandTowards(look.x, look.y, look.z).inflate(1.5D), EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        double d0= -1.0D;
        for (LivingEntity entity1 : list){
            if (entity1 != caster){
                AABB axisalignedbb = entity1.getBoundingBox();
                Optional<Vec3> raytraceresult = axisalignedbb.clip(start, end);
                if (raytraceresult.isPresent()){
                    double d1 = getDistSqCompensated(entity1, caster);
                    if ((d1 < d0 || d0 == -1.0D) && d1 < range * range) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }
        return entity;
    }

    private static double getDistSqCompensated(LivingEntity from, LivingEntity to) {
        double x = from.getX() - to.getX();
        x = Math.max(Math.abs(x) - ((from.getBbWidth() / 2) + (to.getBbWidth() / 2)), 0);
        //stupid inconsistent game
        double y = (from.getY() + from.getBbHeight() / 2) - (to.getY() + to.getBbHeight() / 2);
        y = Math.max(Math.abs(y) - (from.getBbHeight() / 2 + to.getBbHeight() / 2), 0);
        double z = from.getZ() - to.getZ();
        z = Math.max(Math.abs(z) - (from.getBbWidth() / 2 + to.getBbWidth() / 2), 0);
        double me = x * x + y * y + z * z;
        double you = from.distanceToSqr(to);
        return Math.min(me, you);
    }
}
