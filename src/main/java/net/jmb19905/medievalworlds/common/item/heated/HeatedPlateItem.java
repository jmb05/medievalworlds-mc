package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedPlateItem extends AbstractHeatedItem {

    private ResourceLocation anvilTexture;

    public HeatedPlateItem(Item basePlate, String material, Properties properties) {
        super(basePlate, properties);
        this.anvilTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + ".png");
    }

    protected void setAnvilTexture(ResourceLocation anvilTexture) {
        this.anvilTexture = anvilTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return anvilTexture;
    }

    @Override
    public String getType() {
        return "plate";
    }


}
