package com.kkrotello.setofskills;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SkillCasterProcedure {
    public static void execute (Level world, Player user, double x, double y, double z){
        user.displayClientMessage(Component.literal("Test test"), true);
    }
}
