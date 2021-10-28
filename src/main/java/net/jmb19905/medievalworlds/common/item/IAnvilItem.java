package net.jmb19905.medievalworlds.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public interface IAnvilItem {
    Item getBaseItem();
    ResourceLocation getTexture();
    String getType();
}
