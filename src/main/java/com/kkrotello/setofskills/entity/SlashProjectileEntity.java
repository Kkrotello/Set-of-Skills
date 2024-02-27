package com.kkrotello.setofskills.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SlashProjectileEntity extends AbstractHurtingProjectile {
    public SlashProjectileEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public SlashProjectileEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }
    public SlashProjectileEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }
    public SlashProjectileEntity(Level pLevel) {
        super(ModEntities.SLASH_PROJECTILE.get(), pLevel);
    }

    public static int ticksdespawn = 60;

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected float getInertia() {
        return super.getInertia();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            Entity target = pResult.getEntity();
            Entity shooter = this.getOwner();
            Level world = this.level();
            Vec3 look = this.getLookAngle();
            target.hurt(this.damageSources().generic(), 10);
            target.addDeltaMovement(new Vec3(look.x,1, look.z));
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            BlockState block = (this.level().getBlockState(blockPosition()));
            if (block.getBlock() == Blocks.OAK_LOG){
                this.level().setBlock(blockPosition(), Blocks.AIR.defaultBlockState(), 3);
            }

            this.discard();
        }
        super.onHitBlock(pResult);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
