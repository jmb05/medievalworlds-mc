package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedBloomItem extends AbstractHeatedItem {

    private final ResourceLocation anvilIngotTexture;

    public HeatedBloomItem(Item baseItem, String material, Properties properties) {
        super(baseItem, properties);
        this.anvilIngotTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + "_bloom.png");
    }

    @Override
    public ResourceLocation getTexture() {
        return anvilIngotTexture;
    }

    @Override
    public String getType() {
        return "bloom";
    }
}
