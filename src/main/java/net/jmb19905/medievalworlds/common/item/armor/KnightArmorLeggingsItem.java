package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class KnightArmorLeggingsItem extends AbstractKnightArmorItem {

    public KnightArmorLeggingsItem(ArmorMaterial material, Properties properties, String materialString) {
        super(material, EquipmentSlot.LEGS, properties, materialString);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/" + materialString + "_knight_layer_2.png";
    }
}
