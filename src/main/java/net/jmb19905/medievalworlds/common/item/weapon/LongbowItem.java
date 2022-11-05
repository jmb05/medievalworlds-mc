package net.jmb19905.medievalworlds.common.item.weapon;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LongbowItem extends BowItem {

    public LongbowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ClientSetup.getBEWLR();
            }
        });
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity living, int timeLeft) {
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

                float charge = getPowerForTime(i);//from 0 to 1
                if (!((double)charge < 0.1D)) {
                    boolean hasInfinity = player.isCreative() || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, player));
                    if (!level.isClientSide) {
                        ArrowItem arrowItem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow arrow = arrowItem.createArrow(level, itemstack, player);
                        arrow = customArrow(arrow);
                        Vec3 lookVector = player.getLookAngle();
                        arrow.shoot(lookVector.x, lookVector.y, lookVector.z, charge * 4.0F, 0.1F);
                        if (charge == 1.0F) {
                            arrow.setCritArrow(true);
                        }

                        int powerLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, living);
                        if (powerLvl > 0) {
                            arrow.setBaseDamage(arrow.getBaseDamage() + (double)powerLvl * 0.5D + 0.5D);
                        }

                        int punchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, living);
                        if (punchLvl > 0) {
                            arrow.setKnockback(punchLvl + 1);
                        }else{
                            arrow.setKnockback(1);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, living) > 0) {
                            arrow.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, player, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(player.getUsedItemHand()));
                        if (hasInfinity || player.isCreative() && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        arrow.setBaseDamage(arrow.getBaseDamage() + 0.25);
                        level.addFreshEntity(arrow);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
                    if (!hasInfinity && !player.isCreative()) {
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
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }
}
