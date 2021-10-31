package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedPlateItem extends AbstractHeatedItem {

    private ResourceLocation anvilTexture;
    private final int size;

    public HeatedPlateItem(Item basePlate, String material, int size, Properties properties) {
        super(basePlate, properties);
        this.anvilTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + ".png");
        this.size = size;
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
        if(size == 0) {
            return "plate";
        }else if(size == 1) {
            return "medium_plate";
        }else {
            return "large_plate";
        }
    }
}