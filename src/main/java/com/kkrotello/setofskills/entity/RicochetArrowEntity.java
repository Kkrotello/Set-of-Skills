package com.kkrotello.setofskills.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Random;

public class RicochetArrowEntity extends AbstractArrow {
    private int hitcount = 5;
    private boolean lockedin;
    private Entity lockedtarget;
    private Entity owner;

    protected RicochetArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public RicochetArrowEntity(Level pLevel) {
        super(ModEntities.RICHOCHET_ARROW.get(), pLevel);
    }

    public void setOwner(Entity pOwn){
        this.owner = pOwn;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos pos = pResult.getBlockPos();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Direction d = pResult.getDirection();
        Level world = this.level();
        Entity target = null;
        Vec3 Vec = this.getDeltaMovement();

        if (!(world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 18, 18, 18), e -> true).isEmpty())){
            target = (Entity) world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 4, 4, 4), e -> true).stream().sorted(new Object() {
                Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                    return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
                }
            }.compareDistOf(x, y, z)).findFirst().orElse(null);

            if(target == null || target == this.owner){
                bbounce(Vec, x, y, z, d, pResult);
            }else{
                double realtargety = target.getEyeY();
                Vec3 realtarget = new Vec3(target.getX(), realtargety, target.getZ());
                this.setDeltaMovement(this.position().vectorTo(realtarget).scale(0.3));
                this.lockedin = true;
                this.lockedtarget = target;
            }

        }else {
            bbounce(Vec, x, y, z, d, pResult);
        }
        this.hitcount -= 1;
    }

    private void bbounce(Vec3 Vec, double x, double y, double z, Direction d, BlockHitResult pResult) {
        double vx = Vec.x;
        double vy = Vec.y;
        double vz = Vec.z;
        if(this.hitcount>=0) {
            if (d.getStepX() != 0) {
                vx = vx * -1;
            } else if (d.getStepY() != 0) {
                vy = vy * -1;
            } else if (d.getStepZ() != 0) {
                vz = vz * -1;
            }
        }else{
            this.discard();
        }
        Vec3 Bounce = new Vec3(vx, vy, vz);
        this.setPos(pResult.getLocation());
        this.setDeltaMovement(Bounce);
    }


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
//        BlockPos pos = pResult.getEntity().getOnPos();
//        Direction d = pResult.;

//        Entity target = pResult.getEntity();
//        Entity user = this.getOwner();
//        if(target != user){
//            target.hurt(this.damageSources().arrow(this, user), 5f);
//        }
//        if(this.lockedin){
//            this.lockedin = false;
//        }
//
//        Vec3 Vec = this.getDeltaMovement();
//        double vx = Vec.x;
//        double vy = Vec.y;
//        double vz = Vec.z;
//
//        Random rand = new Random();
//        int rx = rand.nextInt(2);
//        int ry = rand.nextInt(2);
//        int rz = rand.nextInt(2);
//
//        if(rx==1){
//            vx = vx * -1;
//        }
//        if (ry==1) {
//            vy = vy * -1;
//        }
//        if (rz==1) {
//            vz = vz * -1;
//        }
//
//        Vec3 Bounce = new Vec3(vx, vy, vz);
//        this.setPos(pResult.getLocation());
//        this.setDeltaMovement(Bounce);
        if(this.lockedin) {
            this.lockedin = false;
        }
        super.onHitEntity(pResult);
        this.hitcount -= 1;
    }



    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(ParticleTypes.CRIT, true, 0, 0, 0, 0, 0,0);
        if(this.lockedin){
            this.setDeltaMovement(new Vec3(((lockedtarget.getX() - this.getX()) * 0.2),
                    ((lockedtarget.getEyeY() - this.getY()) * 0.2),
                    ((lockedtarget.getZ() - this.getZ()) * 0.2)));

//            this.setDeltaMovement(this.position().vectorTo(new Vec3(lockedtarget.getX(), lockedtarget.getY() + 0.6, lockedtarget.getZ())).scale(1));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.SLIME_BLOCK_HIT;
    }
}
