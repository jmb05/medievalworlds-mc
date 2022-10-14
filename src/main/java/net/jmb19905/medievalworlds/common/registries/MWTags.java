package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MWTags {

    public static final TagKey<Item> STAFFS = ItemTags.create(new ResourceLocation(MedievalWorlds.MOD_ID, "staffs"));

}
