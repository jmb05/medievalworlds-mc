package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public class MWItemModelProvider extends ItemModelProvider {

    public MWItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(MWItems.STEEL_INGOT.get());
        simpleItem(MWItems.SILVER_INGOT.get());
        simpleItem(MWItems.BRONZE_INGOT.get());
        simpleItem(MWItems.TIN_INGOT.get());

        simpleItem(MWItems.STEEL_NUGGET.get());
        simpleItem(MWItems.SILVER_NUGGET.get());
        simpleItem(MWItems.BRONZE_NUGGET.get());
        simpleItem(MWItems.TIN_NUGGET.get());

        simpleItem(MWItems.RAW_SILVER.get());
        simpleItem(MWItems.RAW_TIN.get());

        simpleItem(MWItems.CLOAK.get());
        simpleItem(MWItems.DARK_CLOAK.get());
        simpleItem(MWItems.LIGHT_CLOAK.get());

        staff(MWItems.OAK_STAFF.get(),
                new ResourceLocation("block/oak_log"),
                new ResourceLocation("block/oak_log_top"));

        staff(MWItems.SPRUCE_STAFF.get(),
                new ResourceLocation("block/spruce_log"),
                new ResourceLocation("block/spruce_log_top"));

        staff(MWItems.DARK_OAK_STAFF.get(),
                new ResourceLocation("block/dark_oak_log"),
                new ResourceLocation("block/dark_oak_log_top"));

        staff(MWItems.BIRCH_STAFF.get(),
                new ResourceLocation("block/birch_log"),
                new ResourceLocation("block/birch_log_top"));

        staff(MWItems.ACACIA_STAFF.get(),
                new ResourceLocation("block/acacia_log"),
                new ResourceLocation("block/acacia_log_top"));

        staff(MWItems.JUNGLE_STAFF.get(),
                new ResourceLocation("block/jungle_log"),
                new ResourceLocation("block/jungle_log_top"));

        staff(MWItems.MANGROVE_STAFF.get(),
                new ResourceLocation("block/mangrove_log"),
                new ResourceLocation("block/mangrove_log_top"));

        hammer(MWItems.IRON_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_hammer"));
        forgeHammer(MWItems.IRON_FORGE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_forge_hammer"));
        longsword(MWItems.IRON_LONGSWORD.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_weapon"));
        longAxe(MWItems.IRON_LONG_AXE.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_weapon"));

        withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(MWBlocks.ALLOY_FURNACE_ITEM.get())).toString(),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/alloy_furnace"));
    }

    @SuppressWarnings("UnusedReturnValue")
    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()));
    }

    private ItemModelBuilder longAxe(Item longAxeItem, ResourceLocation texture) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(longAxeItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/long_axe_template"))
                .texture("0", texture)
                .texture("particle", texture);
    }

    private ItemModelBuilder longsword(Item longswordItem, ResourceLocation texture) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(longswordItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/longsword_template"))
                .texture("0", texture)
                .texture("particle", texture);
    }

    private ItemModelBuilder hammer(Item hammerItem, ResourceLocation texture) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(hammerItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/hammer_template"))
                .texture("0", texture)
                .texture("particle", texture);
    }

    private ItemModelBuilder forgeHammer(Item forgeHammerItem, ResourceLocation texture) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(forgeHammerItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/forge_hammer_template"))
                .texture("0", texture)
                .texture("particle", texture);
    }

    private ItemModelBuilder staff(Item staffItem, ResourceLocation logSide, ResourceLocation logTop) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(staffItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/staff_template"))
                .texture("0", logSide)
                .texture("1", logTop)
                .texture("particle", logSide);
    }

    private ItemModelBuilder layeredTextureChildItem(Item item, String parent, String... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(), new ResourceLocation(parent));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture(String.valueOf(i), new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + textures[i]));
        }
        return builder;
    }

    private ItemModelBuilder layeredTextureChildItem(Item item, ResourceLocation parent, ResourceLocation... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(), parent);
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture(String.valueOf(i), textures[i]);
        }
        return builder;
    }

    private ItemModelBuilder layeredTextureItem(Item item, String... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(), new ResourceLocation("item/generated"));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture("layer" + i, new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + textures[i]));
        }
        return builder;
    }

    private ItemModelBuilder layeredTextureItem(Item item, ResourceLocation... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(), new ResourceLocation("item/generated"));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture("layer" + i, textures[i]);
        }
        return builder;
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()));
    }
    
}
