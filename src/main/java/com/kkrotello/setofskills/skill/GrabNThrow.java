package com.kkrotello.setofskills.skill;

import com.kkrotello.setofskills.util.RayTrace;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public class GrabNThrow {

    public static HashMap<Player, LivingEntity> grabmap = new HashMap<Player, LivingEntity>();

    public static void execute(Level world, Player user){
        LivingEntity grabbed = RayTrace.Raytracetarget(world, user, 3);
        if(grabbed != null){
            grabmap.put(user, grabbed);
            user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));
        } else{
            user.displayClientMessage(Component.literal("no target"), true);
        }
    }

    public static void tick(Player player){

        if (player.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null) {
            if(grabmap.containsKey(player)){
                LivingEntity grabbed = grabmap.get(player);
                grabbed.resetFallDistance();
                grabbed.teleportTo(player.getX(), player.getY() + 1, player.getZ());
            }
        }
        else if (grabmap.containsKey(player)) {
            LivingEntity grabbed = grabmap.get(player);
            if(player.onGround()){
                grabbed.setDeltaMovement(player.getLookAngle().normalize());
            }
            grabmap.remove(player);
        }
    }
}
