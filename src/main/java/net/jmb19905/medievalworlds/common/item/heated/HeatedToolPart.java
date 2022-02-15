package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedToolPart extends AbstractHeatedItem{

    private final String partType;
    private final ResourceLocation anvilTexture;

    public HeatedToolPart(Item baseItem, String material, String partType, Properties properties) {
        super(baseItem, properties);
        this.partType = partType;
        this.anvilTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + partType + "/" + material + ".png");
    }

    @Override
    public ResourceLocation getAnvilTexture() {
        return anvilTexture;
    }

    @Override
    public String getType() {
        return partType;
    }
}
