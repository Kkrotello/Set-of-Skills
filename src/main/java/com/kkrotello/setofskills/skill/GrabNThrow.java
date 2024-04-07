package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.util.RayTrace;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrabNThrow {

    public static int counter;
    public static Entity caster;
    public static Entity target;
    public static Level world2;
    public static boolean startcount = false;
    public static void execute(Level world, Player user){
        startcount = true;
        counter = 0;
        user.displayClientMessage(Component.literal("kys"), true);
        caster = (Entity)user;
        world2 = world;
        LivingEntity caught = RayTrace.Raytracetarget(world, user, 3);
        target = (Entity) caught;
        if(caught!=null){
            Vec3 overpos = new Vec3(user.getX(), user.getY() + 1, user.getZ());
            caught.startRiding(caster);
            caught.setPose(Pose.SITTING);
        }

    }



    public static void tick(Player player){
        if(startcount){
            if(counter < 60) {
                counter++;
            } else if (counter >= 60 && counter < 62){
//                target.hurt(new DamageSource(world2.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 10);
//                caster.displayClientMessage(Component.literal("killed"),true);
                target = caster.getFirstPassenger();
                counter++;
            } else if (counter >= 62) {
                caster.ejectPassengers();
                if (target!=null) {
                    target.setPos(caster.position());
                    target.setDeltaMovement(new Vec3(caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z));
                    target.hurt(new DamageSource(world2.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 10);
                }
                startcount = false;
                counter = 0;
            }
        }
    }
}
