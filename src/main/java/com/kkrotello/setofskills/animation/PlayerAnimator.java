package com.kkrotello.setofskills.animation;

import com.kkrotello.setofskills.SetOfSkills;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SetOfSkills.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerAnimator {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //Set the player construct callback. It can be a lambda function.
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(SetOfSkills.MODID, "animation"),
                42,
                PlayerAnimator::registerPlayerAnimation);
    }

    //This method will set your mods animation into the library.
    private static  IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }
}
