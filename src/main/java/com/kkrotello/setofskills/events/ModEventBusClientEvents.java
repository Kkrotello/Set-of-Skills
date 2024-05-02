package com.kkrotello.setofskills.events;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.entity.client.ModModelLayers;
import com.kkrotello.setofskills.entity.client.SlashProjectileModel;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = SetOfSkills.MODID , bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SLASH_PROJECTIL_LAYER, SlashProjectileModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent event) {
        //Test if it is a player (main or other) and the message
        if (event.getMessage().contains(Component.literal("waving"))) {


            //Get the player from Minecraft, using the chat profile ID. From network packets, you'll receive entity IDs instead of UUIDs
            var player = Minecraft.getInstance().level.getPlayerByUUID(event.getSender());

            if (player == null) return; //The player can be null because it was a system message or because it is not loaded by this player.

            //Get the animation for that player
            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player)
                    .get(new ResourceLocation(SetOfSkills.MODID, "animation"));
            if (animation != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail

                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("examplemod", "waving"))));
                //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                //See javadoc for details
            }
        }
    }
}
