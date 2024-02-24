package com.kkrotello.setofskills.events;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.entity.client.ModModelLayers;
import com.kkrotello.setofskills.entity.client.SlashProjectileModel;
import net.minecraftforge.api.distmarker.Dist;
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
}
