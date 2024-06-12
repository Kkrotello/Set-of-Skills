package com.kkrotello.setofskills.skill;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AirJump extends BaseSkill{

//    public static void execute(Player user, Level world) {
//        user.setDeltaMovement(new Vec3((user.getLookAngle().x), (user.getLookAngle().y)*1.5, (user.getLookAngle().z)));
////        if(!world.isClientSide()){
////            world.playSound(user);
////        }

    @Override
    public void execute(Player user, Level world) {
        user.setDeltaMovement(new Vec3((user.getLookAngle().x), (user.getLookAngle().y)*1.5, (user.getLookAngle().z)));
        super.execute(user, world);
        if (!user.level().isClientSide()){
            user.displayClientMessage(Component.literal("jump"), true);
        }
    }
}
