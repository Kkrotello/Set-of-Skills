package com.kkrotello.setofskills.entity.client;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.entity.SlashProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.security.PublicKey;

public class SlashProjectileRenderer extends EntityRenderer<SlashProjectileEntity> {

    private SlashProjectileModel model;
    public SlashProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new SlashProjectileModel(pContext.bakeLayer(ModModelLayers.SLASH_PROJECTIL_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(SlashProjectileEntity slashProjectileEntity) {
        return new ResourceLocation(SetOfSkills.MODID, "textures/entity/slash_projectile.png");
    }

    @Override
    public void render(SlashProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
