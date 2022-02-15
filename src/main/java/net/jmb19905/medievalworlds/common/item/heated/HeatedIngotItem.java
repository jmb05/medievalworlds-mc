package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedIngotItem extends AbstractHeatedItem {

    private final ResourceLocation anvilIngotTexture;

    public HeatedIngotItem(Item baseIngot, String material, Properties properties) {
        super(baseIngot, properties);
        this.anvilIngotTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/ingot/" + material + ".png");
    }

    @Override
    public ResourceLocation getAnvilTexture() {
        return anvilIngotTexture;
    }

    @Override
    public String getType() {
        return "ingot";
    }

}
