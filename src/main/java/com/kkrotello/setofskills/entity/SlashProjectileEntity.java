package com.kkrotello.setofskills.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SlashProjectileEntity extends AbstractArrow {
//    public SlashProjectileEntity(EntityType<? extends > pEntityType, Level pLevel) {
//        super(pEntityType, pLevel);
//    }
//    public SlashProjectileEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
//        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
//    }
//    public SlashProjectileEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
//        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
//    }
//    public SlashProjectileEntity(Level pLevel) {
//        super(ModEntities.SLASH_PROJECTILE.get(), pLevel);
//    }

    public static int ticksdespawn = 60;

    public SlashProjectileEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SlashProjectileEntity(Level pLevel){
        super(ModEntities.SLASH_PROJECTILE.get(), pLevel);
    }

//    @Override
//    protected boolean shouldBurn() {
//        return false;
//    }
//
//    @Override
//    protected float getInertia() {
//        return super.getInertia();
//    }


    public void specialshoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(this.random.triangle(0.0, 0.0172275 * (double)pInaccuracy), this.random.triangle(0.0, 0.0172275 * (double)pInaccuracy), this.random.triangle(0.0, 0.0172275 * (double)pInaccuracy)).scale((double)pVelocity);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot(90f);
        this.setXRot(90f);
//        this.yRotO = this.getYRot();
//        this.xRotO = this.getXRot();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            Entity target = pResult.getEntity();
            Entity shooter = this.getOwner();
            Vec3 knock = this.getDeltaMovement();
            if(target != shooter){
                target.hurt(this.damageSources().generic(), 10);
                target.addDeltaMovement(new Vec3(knock.x,0.5, knock.z));
            }
        }
        this.discard();
    }

    @Override
    public double getBaseDamage() {
        return super.getBaseDamage();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            BlockState block = (this.level().getBlockState(blockPosition()));
            if (block.getBlock() == Blocks.OAK_LOG){
                this.level().setBlock(blockPosition(), Blocks.AIR.defaultBlockState(), 3);
            }
            BlockPos pos = pResult.getBlockPos();
            Direction d = pResult.getDirection();

            Vec3 Vec = this.getDeltaMovement();
            double vx = Vec.x;
            double vy = Vec.y;
            double vz = Vec.z;

            if(d.getStepX() != 0){
                vx = vx * -1;
            } else if (d.getStepY() != 0) {
                vy = vy * -1;
            } else if (d.getStepZ() != 0) {
                vz = vz * -1;
            }

            Vec3 Bounce = new Vec3(vx, vy, vz);
            this.setPos(pResult.getLocation());
            this.setDeltaMovement(Bounce);

            this.discard();
        }
        super.onHitBlock(pResult);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getPersistentData().getBoolean("Grav")) {
            this.getPersistentData().putBoolean("Grav", true);
            this.setNoGravity(true);
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }
}
