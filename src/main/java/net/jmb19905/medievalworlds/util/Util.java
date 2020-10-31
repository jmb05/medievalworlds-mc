package net.jmb19905.medievalworlds.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Util {

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int percentage(int max, int min, int curr){
        int returnVal;
        if(max > min){
            returnVal = 100/max*curr;
        }else if(max < min){
            curr = Math.abs(curr-min);
            returnVal = 100/min*curr;
        }else{
            throw new IllegalStateException("max ("+max+") and min ("+min+") are the same");
        }
        return returnVal;
    }

    //attacker code is from Item#rayCast
    public static BlockRayTraceResult rayTraceBlocks(World worldIn, LivingEntity entity, RayTraceContext.FluidMode fluidMode, double reach) {
        float f = entity.rotationPitch;
        float f1 = entity.rotationYaw;
        Vec3d vec3d = entity.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3d vec3d1 = vec3d.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, entity));
    }

    //Found attacker in
    public static EntityRayTraceResult rayTraceEntities(World worldIn, LivingEntity entityIn, RayTraceContext.FluidMode fluidMode, double reach) {
        return null;
    }

    public static List<BlockPos> calcMegaMinerBreaking(ServerWorld world, PlayerEntity player, ItemStack stack, BlockPos origin){
        List<BlockPos> output = new ArrayList<>();
        Direction direction = Util.rayTraceBlocks(world, player, RayTraceContext.FluidMode.ANY, 50).getFace();
        if (direction == Direction.DOWN || direction == Direction.UP) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY(), origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY(), newPos.getZ() + j);
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY() + j, newPos.getZ());
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        } else {
            BlockPos newPos = new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX(), newPos.getY() + j, newPos.getZ() + i);
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        }
        return output;
    }
    
    public static void attackWithStack(PlayerEntity attacker, Entity targetEntity, float attackDamage){
        if (targetEntity.canBeAttackedWithItem()) {
            if (!targetEntity.hitByEntity(attacker)) {
                float f = attackDamage;
                float f1;
                if (targetEntity instanceof LivingEntity) {
                    f1 = EnchantmentHelper.getModifierForCreature(attacker.getHeldItemMainhand(), ((LivingEntity)targetEntity).getCreatureAttribute());
                } else {
                    f1 = EnchantmentHelper.getModifierForCreature(attacker.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
                }

                float f2 = attacker.getCooledAttackStrength(0.5F);
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                attacker.resetCooldown();
                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(attacker);
                    if (attacker.isSprinting() && flag) {
                        attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, attacker.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && attacker.fallDistance > 0.0F && !attacker.onGround && !attacker.isOnLadder() && !attacker.isInWater() && !attacker.isPotionActive(Effects.BLINDNESS) && !attacker.isPassenger() && targetEntity instanceof LivingEntity;
                    flag2 = flag2 && !attacker.isSprinting();
                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(attacker, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
                    flag2 = hitResult != null;
                    if (flag2) {
                        f *= hitResult.getDamageModifier();
                    }

                    f = f + f1;
                    boolean flag3 = false;
                    double d0 = attacker.distanceWalkedModified - attacker.prevDistanceWalkedModified;
                    if (flag && !flag2 && !flag1 && attacker.onGround && d0 < (double)attacker.getAIMoveSpeed()) {
                        ItemStack itemstack = attacker.getHeldItem(Hand.MAIN_HAND);
                        if (itemstack.getItem() instanceof SwordItem) {
                            flag3 = true;
                        }
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(attacker);
                    if (targetEntity instanceof LivingEntity) {
                        f4 = ((LivingEntity)targetEntity).getHealth();
                        if (j > 0 && !targetEntity.isBurning()) {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    Vec3d vec3d = targetEntity.getMotion();
                    boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(attacker), f);
                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof LivingEntity) {
                                ((LivingEntity)targetEntity).knockBack(attacker, (float)i * 0.5F, MathHelper.sin(attacker.rotationYaw * ((float)Math.PI / 180F)), -MathHelper.cos(attacker.rotationYaw * ((float)Math.PI / 180F)));
                            } else {
                                targetEntity.addVelocity(-MathHelper.sin(attacker.rotationYaw * ((float)Math.PI / 180F)) * (float)i * 0.5F, 0.1D, MathHelper.cos(attacker.rotationYaw * ((float)Math.PI / 180F)) * (float)i * 0.5F);
                            }

                            attacker.setMotion(attacker.getMotion().mul(0.6D, 1.0D, 0.6D));
                            attacker.setSprinting(false);
                        }

                        if (flag3) {
                            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(attacker) * f;

                            for(LivingEntity livingentity : attacker.world.getEntitiesWithinAABB(LivingEntity.class, targetEntity.getBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
                                if (livingentity != attacker && livingentity != targetEntity && !attacker.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingentity).hasMarker()) && attacker.getDistanceSq(livingentity) < 9.0D) {
                                    livingentity.knockBack(attacker, 0.4F, MathHelper.sin(attacker.rotationYaw * ((float)Math.PI / 180F)), -MathHelper.cos(attacker.rotationYaw * ((float)Math.PI / 180F)));
                                    livingentity.attackEntityFrom(DamageSource.causePlayerDamage(attacker), f3);
                                }
                            }

                            attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1.0F, 1.0F);
                            attacker.spawnSweepParticles();
                        }

                        if (targetEntity instanceof ServerPlayerEntity && targetEntity.velocityChanged) {
                            ((ServerPlayerEntity)targetEntity).connection.sendPacket(new SEntityVelocityPacket(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.setMotion(vec3d);
                        }

                        if (flag2) {
                            attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0F, 1.0F);
                            attacker.onCriticalHit(targetEntity);
                        }

                        if (!flag2 && !flag3) {
                            if (flag) {
                                attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, attacker.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, attacker.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (f1 > 0.0F) {
                            attacker.onEnchantmentCritical(targetEntity);
                        }

                        attacker.setLastAttackedEntity(targetEntity);
                        if (targetEntity instanceof LivingEntity) {
                            EnchantmentHelper.applyThornEnchantments((LivingEntity)targetEntity, attacker);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(attacker, targetEntity);
                        ItemStack itemstack1 = attacker.getHeldItemMainhand();
                        Entity entity = targetEntity;
                        if (targetEntity instanceof EnderDragonPartEntity) {
                            entity = ((EnderDragonPartEntity)targetEntity).dragon;
                        }

                        if (!attacker.world.isRemote && !itemstack1.isEmpty() && entity instanceof LivingEntity) {
                            ItemStack copy = itemstack1.copy();
                            itemstack1.hitEntity((LivingEntity)entity, attacker);
                            if (itemstack1.isEmpty()) {
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(attacker, copy, Hand.MAIN_HAND);
                                attacker.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof LivingEntity) {
                            float f5 = f4 - ((LivingEntity)targetEntity).getHealth();
                            attacker.addStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                            if (j > 0) {
                                targetEntity.setFire(j * 4);
                            }

                            if (attacker.world instanceof ServerWorld && f5 > 2.0F) {
                                int k = (int)((double)f5 * 0.5D);
                                ((ServerWorld)attacker.world).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, targetEntity.getPosX(), targetEntity.getPosYHeight(0.5D), targetEntity.getPosZ(), k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        attacker.addExhaustion(0.1F);
                    } else {
                        attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, attacker.getSoundCategory(), 1.0F, 1.0F);
                        if (flag4) {
                            targetEntity.extinguish();
                        }
                    }
                }

            }
        }
    }
    
}
