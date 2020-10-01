package net.jmb19905.medievalworlds.item.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class MegaMinerEnchantment extends Enchantment {

    public MegaMinerEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
