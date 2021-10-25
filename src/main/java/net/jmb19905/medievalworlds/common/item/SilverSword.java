package net.jmb19905.medievalworlds.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import javax.annotation.Nonnull;

public class SilverSword extends SwordItem {

    private final int attackDamage;

    public SilverSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackDamage = attackDamageIn;
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, LivingEntity target, @Nonnull LivingEntity attacker) {
        System.out.println("CreatureType: " + MobType.UNDEAD + " - Mainhand: " + attacker.getUseItem());
        if (target.getMobType() == MobType.UNDEAD) {
            super.getAttributeModifiers(EquipmentSlot.MAINHAND, stack).put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ((double) this.attackDamage + 2), AttributeModifier.Operation.ADDITION));
        }
        System.out.println(super.getAttributeModifiers(EquipmentSlot.MAINHAND, stack).get(Attributes.ATTACK_DAMAGE));
        boolean returnBool = super.hurtEnemy(stack, target, attacker);
        super.getAttributeModifiers(EquipmentSlot.MAINHAND, stack).put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        return returnBool;
    }
}
