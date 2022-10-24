package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class CoifHelmetItem extends ArmorItem {
    public CoifHelmetItem(ArmorMaterial material, Properties properties) {
        super(material, EquipmentSlot.HEAD, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/coif.png";
    }
}