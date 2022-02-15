package net.jmb19905.medievalworlds.core.tabs;

import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWMaterialsTab extends MWSortedTab {

    public MWMaterialsTab() {
        super("medievalworlds.materials");
    }

    @Override
    protected Comparator<ItemStack> getSorter() {
        return toSortingList(MWItems.getMaterialsItemOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("medievalworlds", "steel_pickaxe_head")));
    }
}
