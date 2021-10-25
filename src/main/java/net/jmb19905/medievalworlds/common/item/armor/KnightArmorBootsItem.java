package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class KnightArmorBootsItem extends ArmorItem {

    private final String material;

    public KnightArmorBootsItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, String material) {
        super(materialIn, slot, builder);
        this.material = material;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/"+material+"_knight_layer_1.png";
    }
}
