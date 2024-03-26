package com.kkrotello.setofskills.skill;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class GrabNThrow {

    public static int counter;
    public static Player target;
    public static Level world2;
    public static void execute(Level world, Player user){
        startcount = true;
        counter = 0;
        user.displayClientMessage(Component.literal("kys"), true);
        target = user;
        world2 = world;
    }

    public static boolean startcount = false;

    public void tick(){
        if(startcount){
            if(counter < 30) {
                counter++;
            } else if (counter >= 30) {
                target.hurt(new DamageSource(world2.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), 5);
                startcount = false;
                counter = 0;
            }
        }
    }
}
