package net.jmb19905.medievalworlds.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jmb19905.medievalworlds.common.compatability.MWExternalMods;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

public class MWSwordWeapon extends SwordItem {
    private final float attackRangeBonus;
    private final Multimap<Attribute, AttributeModifier> additionalModifiers;

    public MWSwordWeapon(Tier tier, int attackDamage, float attackSpeed, float attackRangeBonus, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        if (MWExternalMods.BETTER_COMBAT.isLoaded()) attackRangeBonus = 0;
        this.attackRangeBonus = attackRangeBonus;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier("Attack Range modifier", this.attackRangeBonus, AttributeModifier.Operation.ADDITION));
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        this.additionalModifiers = builder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.additionalModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public float getAttackRangeBonus() {
        return attackRangeBonus;
    }
}
