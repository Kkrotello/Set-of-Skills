package com.kkrotello.setofskills;

import com.kkrotello.setofskills.skill.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SkillCasterProcedure {
    public static void execute (Level world, Player user, double x, double y, double z){
        if (user == null)
            return;
        if (user.isShiftKeyDown()) {
            if (!user.level().isClientSide()){
                user.displayClientMessage(Component.literal("jump"), true);
            }
            AirJump.execute(user, world);
        } else if (!user.isShiftKeyDown()) {

                InteractionHand pHand = user.swingingArm;
//                Item myitem = user.getItemInHand(pHand).getItem();
//                user.displayClientMessage(Component.literal("You have: " + I18n.get(myitem.getDescriptionId())), true);
                if ((user.getMainHandItem().getItem() == Items.IRON_BOOTS)){
                    user.displayClientMessage(Component.literal("shot"), true);
                    DropShotKick.execute(world, user);
                } else if ((user.getMainHandItem()).getItem() == Items.DIAMOND_SWORD) {
                    user.displayClientMessage(Component.literal("slash"), true);
                    ArcSlash.execute(world, user, x, y, z);
                } else if ((user.getMainHandItem()).getItem() == Items.BOW) {
                    user.displayClientMessage(Component.literal("slash"), true);
                    Trickshot.execute(world, user);
                } else if ((user.getMainHandItem()).getItem() == Items.LEAD) {
//                    user.displayClientMessage(Component.literal("slash"), true);
                    GrabNThrow.execute(world, user);
                }

        }
    }
}
