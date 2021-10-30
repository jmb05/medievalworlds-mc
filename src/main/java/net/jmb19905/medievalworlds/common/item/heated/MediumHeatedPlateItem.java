package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MediumHeatedPlateItem extends HeatedPlateItem{

    public MediumHeatedPlateItem(Item basePlate, String material, Properties properties) {
        super(basePlate, material, properties);
        setAnvilTexture(new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/medium_" + material + "_plate.png"));
    }

}
