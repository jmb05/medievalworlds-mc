package net.jmb19905.medievalworlds.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

public class MWAxeWeapon extends AxeItem {
    private final float attackRangeBonus;
    private final Multimap<Attribute, AttributeModifier> additionalModifiers;

    public MWAxeWeapon(Tier tier, int attackDamage, float attackSpeed, float attackRangeBonus, Item.Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        this.attackRangeBonus = attackRangeBonus;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier("Attack Range modifier", this.attackRangeBonus, AttributeModifier.Operation.ADDITION));
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        this.additionalModifiers = builder.build();
    }

    // Only takes 1 damage when attacking
    @Override
    public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    // Takes 2 damage when mining
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity user) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(2, user, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.additionalModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public float getAttackRangeBonus() {
        return attackRangeBonus;
    }
}
