package net.jmb19905.medievalworlds.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class SpearItem extends Item {

    private final float attackDamage;
    private final float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public SpearItem(float attackDamage, float attackSpeed, Properties properties) {
        super(properties);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack p_41452_) {
        return UseAnim.NONE;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity, int timeLeft) {
        if(entity instanceof Player player) {
            int i = this.getUseDuration(stack) - timeLeft;
            if(i >= 10) {
                if(!level.isClientSide) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
                    ThrownTrident trident = new ThrownTrident(level, player, stack);
                    Vec3 lookVector = player.getLookAngle();
                    trident.shoot(lookVector.x, lookVector.y, lookVector.z, 0.0f, 1.0f);
                    if(player.isCreative()) {
                        trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        player.getInventory().removeItem(stack);
                    }
                    level.addFreshEntity(trident);
                    //Need to add spear sound
                    //worldIn.playMovingSound(null, tridententity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        }else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, living -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level level, BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entity) {
        if ((double)state.getDestroySpeed(level, pos) != 0.0D) {
            stack.hurtAndBreak(2, entity, (living) -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public int getItemEnchantmentValue() {
        return 1;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
