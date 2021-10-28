package net.jmb19905.medievalworlds.util;

import net.jmb19905.medievalworlds.common.recipes.alloy.AlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.stream.Collectors;

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

    //attacker code is from Item#getPlayerPOVHitResult
    public static BlockHitResult rayTraceBlocks(Level level, LivingEntity entity, ClipContext.Fluid fluidMode, double reach) {
        float f = entity.getXRot();
        float f1 = entity.getYRot();
        Vec3 vec3 = entity.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluidMode, entity));
    }

    public static List<BlockPos> calcMegaMinerBreaking(ServerLevel level, Player player, ItemStack stack, BlockPos origin){
        List<BlockPos> output = new ArrayList<>();
        Direction direction = Util.rayTraceBlocks(level, player, ClipContext.Fluid.ANY, 50).getDirection();
        if (direction == Direction.DOWN || direction == Direction.UP) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY(), origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY(), newPos.getZ() + j);
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && level.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {//TODO: check if block is breakable
                        output.add(currentPos);
                    }
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY() + j, newPos.getZ());
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && level.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {//TODO: check if block is breakable
                        output.add(currentPos);
                    }
                }
            }
        } else {
            BlockPos newPos = new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX(), newPos.getY() + j, newPos.getZ() + i);
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && level.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {//TODO: check if block is breakable
                        output.add(currentPos);
                    }
                }
            }
        }
        return output;
    }

    @SuppressWarnings("unchecked")
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn, Level world) {
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn) {
        ClientLevel world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }


    public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level level){
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = findRecipeByType(typeIn, level);
        for(Recipe<?> recipe : recipes){
            if(typeIn == MWRecipeSerializers.ALLOY_TYPE) {
                NonNullList<ItemStack> inputList = ((AlloyRecipe) recipe).getInputs();
                inputs.addAll(inputList);
            }else if(typeIn == MWRecipeSerializers.ANVIL_TYPE) {
                inputs.add(((AnvilRecipe) recipe).getInput());
            }
        }
        return inputs;
    }

    public static Set<ItemStack> getAllRecipeInput(RecipeType<?> typeIn, Level level) {
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = findRecipeByType(typeIn, level);
        for(Recipe<?> recipe : recipes){
            inputs.add(recipe.getResultItem());
        }
        return inputs;
    }

    /*public static void attackWithStack(Player attacker, Entity targetEntity, float attackDamage){
        if (targetEntity.isAttackable()) {
            if (!targetEntity.skipAttackInteraction(attacker)) {
                float f = attackDamage;
                float f1;
                if (targetEntity instanceof LivingEntity) {
                    f1 = EnchantmentHelper.getDamageBonus(attacker.getMainHandItem(), ((LivingEntity)targetEntity).getMobType());
                } else {
                    f1 = EnchantmentHelper.getDamageBonus(attacker.getMainHandItem(), MobType.UNDEFINED);
                }

                float f2 = attacker.getAttackStrengthScale(0.5F);
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                attacker.resetAttackStrengthTicker();
                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackBonus(attacker);
                    if (attacker.isSprinting() && flag) {
                        attacker.level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, attacker.getSoundSource(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && attacker.fallDistance > 0.0F && !attacker.isOnGround() && !attacker.onClimbable() && !attacker.isInWater() && !attacker.hasEffect(MobEffects.BLINDNESS) && !attacker.isPassenger() && targetEntity instanceof LivingEntity;
                    flag2 = flag2 && !attacker.isSprinting();
                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(attacker, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
                    flag2 = hitResult != null;
                    if (flag2) {
                        f *= hitResult.getDamageModifier();
                    }

                    f = f + f1;
                    boolean flag3 = false;
                    double d0 = attacker.walkDist - attacker.walkDistO;
                    if (flag && !flag2 && !flag1 && attacker.isOnGround() && d0 < (double)attacker.getSpeed()) {
                        ItemStack itemstack = attacker.getItemInHand(InteractionHand.MAIN_HAND);
                        if (itemstack.getItem() instanceof SwordItem) {
                            flag3 = true;
                        }
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspect(attacker);
                    if (targetEntity instanceof LivingEntity) {
                        f4 = ((LivingEntity)targetEntity).getHealth();
                        if (j > 0 && !targetEntity.isOnFire()) {
                            flag4 = true;
                            targetEntity.setSecondsOnFire(1);
                        }
                    }

                    Vec3 vec3d = targetEntity.getDeltaMovement();
                    boolean flag5 = targetEntity.hurt(DamageSource.playerAttack(attacker), f);
                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof LivingEntity) {
                                ((LivingEntity)targetEntity).knockback((float)i * 0.5F, Mth.sin(attacker.yBodyRot * ((float)Math.PI / 180F)), -Mth.cos(attacker.yBodyRot * ((float)Math.PI / 180F)));
                            } else {
                                targetEntity.moveTo(-Mth.sin(attacker.yBodyRot * ((float)Math.PI / 180F)) * (float)i * 0.5F + targetEntity.getX(), 0.1D + targetEntity.getY(), Mth.cos(attacker.yBodyRot * ((float)Math.PI / 180F)) * (float)i * 0.5F + targetEntity.getZ());
                            }

                            attacker.setDeltaMovement(attacker.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                            attacker.setSprinting(false);
                        }

                        if (flag3) {
                            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(attacker) * f;

                            for(LivingEntity livingentity : attacker.level.getEntitiesOfClass(LivingEntity.class, targetEntity.getBoundingBox().contract(1.0D, 0.25D, 1.0D))) {
                                if (livingentity != attacker && livingentity != targetEntity && !attacker.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !livingentity.hurtMarked) && attacker.distanceToSqr(livingentity) < 9.0D) {
                                    livingentity.knockback(0.4F, Mth.sin(attacker.yBodyRot * ((float)Math.PI / 180F)), -Mth.cos(attacker.yBodyRot * ((float)Math.PI / 180F)));
                                    livingentity.hurt(DamageSource.playerAttack(attacker), f3);
                                }
                            }

                            attacker.level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
                            attacker.sweepAttack();
                        }

                        if (targetEntity instanceof ServerPlayer && targetEntity.) {
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
    }*/
    
}
