package com.kkrotello.setofskills.skill;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AirJump {
    public static void execute(Player user, Level world) {
        user.setDeltaMovement(new Vec3((user.getLookAngle().x), (user.getLookAngle().y)*1.5, (user.getLookAngle().z)));
//        if(!world.isClientSide()){
//            world.playSound(user);
//        }
    }
}
