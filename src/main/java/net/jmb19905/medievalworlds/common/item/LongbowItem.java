package net.jmb19905.medievalworlds.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Random;

public class LongbowItem extends BowItem {

    private final Random random;

    public LongbowItem(Item.Properties builder) {
        super(builder);
        this.random = new Random();
    }

    @Override
    public void releaseUsing(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity living, int timeLeft) {
        if (living instanceof Player player) {
            boolean flag = player.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY_ARROWS, living) > 0;
            ItemStack itemstack = player.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, level, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.isCreative() || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, player));
                    if (!level.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrowentity = arrowitem.createArrow(level, itemstack, player);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        Vec3 lookVector = player.getLookAngle();
                        abstractarrowentity.shoot(lookVector.x, lookVector.y, lookVector.z, f * 4.0F, 0.1F);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, living);
                        if (j > 0) {
                            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, living);
                        if (k > 0) {
                            abstractarrowentity.setKnockback(k + 1);
                        }else{
                            abstractarrowentity.setKnockback(1);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, living) > 0) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, player, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(player.getUsedItemHand()));
                        if (flag1 || player.isCreative() && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + 0.25);
                        level.addFreshEntity(abstractarrowentity);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.isCreative()) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 100000;
    }
}
