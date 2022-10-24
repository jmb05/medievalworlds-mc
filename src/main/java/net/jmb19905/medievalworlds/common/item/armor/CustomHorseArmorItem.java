package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;

public class CustomHorseArmorItem extends HorseArmorItem {

    public CustomHorseArmorItem(int prot, String material, Properties properties) {
        super(prot, material, properties);
    }

    public CustomHorseArmorItem(int prot, ResourceLocation texture, Properties properties) {
        super(prot, texture, properties);
    }
}