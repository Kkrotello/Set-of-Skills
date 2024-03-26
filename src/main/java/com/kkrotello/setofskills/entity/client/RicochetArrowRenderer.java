package com.kkrotello.setofskills.entity.client;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RicochetArrowRenderer extends ArrowRenderer {
    public RicochetArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return new ResourceLocation(SetOfSkills.MODID, "textures/entity/ricochet_arrow.png");
    }
}
