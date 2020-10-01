package net.jmb19905.medievalworlds.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class KnightArmorLeggingsItem extends ArmorItem {

    private final String material;

    public KnightArmorLeggingsItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, String material) {
        super(materialIn, slot, builder);
        this.material = material;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "medievalworlds:textures/models/armor/"+material+"_knight_layer_2.png";
    }
}
