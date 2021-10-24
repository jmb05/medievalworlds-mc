package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.ResourceLocation;

public class NetheriteHorseArmor extends HorseArmorItem {
    public NetheriteHorseArmor() {
        super(20, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), new Properties().maxStackSize(1).group(MedievalWorlds.itemGroup));
    }
}
