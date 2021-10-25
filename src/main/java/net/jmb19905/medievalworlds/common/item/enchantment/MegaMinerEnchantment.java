package net.jmb19905.medievalworlds.common.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MegaMinerEnchantment extends Enchantment {

    public MegaMinerEnchantment(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
