package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;

public class NetheriteHorseArmor extends HorseArmorItem {
    public NetheriteHorseArmor() {
        super(20, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), new Properties().stacksTo(1).tab(MedievalWorlds.toolsTab));
    }
}
