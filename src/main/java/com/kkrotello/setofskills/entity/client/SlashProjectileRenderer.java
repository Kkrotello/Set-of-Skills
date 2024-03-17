package com.kkrotello.setofskills.entity.client;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.entity.SlashProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.security.PublicKey;

@OnlyIn(Dist.CLIENT)
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
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) + 180.0f));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
//        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
//        float $$6 = Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
//        float $$7 = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());
        VertexConsumer $$8 = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
//        this.model.setupAnim(0.0F, $$6, $$7);
        this.model.renderToBuffer(pPoseStack, $$8, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
