package net.jmb19905.medievalworlds.common.item;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import javax.annotation.Nonnull;

public class SilverSword extends SwordItem {

    private final int attackDamage;

    public SilverSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackDamage = attackDamageIn;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, LivingEntity target, @Nonnull LivingEntity attacker) {
        System.out.println("CreatureType: " + CreatureAttribute.UNDEAD + " - Mainhand: " + attacker.getHeldItemMainhand());
        if (target.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            super.getAttributeModifiers(EquipmentSlotType.MAINHAND).put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ((double) this.attackDamage + 2), AttributeModifier.Operation.ADDITION));
        }
        System.out.println(super.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE));
        boolean returnBool = super.hitEntity(stack, target, attacker);
        super.getAttributeModifiers(EquipmentSlotType.MAINHAND).put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        return returnBool;
    }
}
