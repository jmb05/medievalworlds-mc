package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.BiConsumer;

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

        simpleItem(MWItems.BRONZE_PICKAXE.get());
        simpleItem(MWItems.BRONZE_AXE.get());
        simpleItem(MWItems.BRONZE_SHOVEL.get());
        simpleItem(MWItems.BRONZE_HOE.get());
        simpleItem(MWItems.BRONZE_SWORD.get());

        simpleItem(MWItems.STEEL_PICKAXE.get());
        simpleItem(MWItems.STEEL_AXE.get());
        simpleItem(MWItems.STEEL_SHOVEL.get());
        simpleItem(MWItems.STEEL_HOE.get());
        simpleItem(MWItems.STEEL_SWORD.get());

        simpleItem(MWItems.SILVER_PICKAXE.get());
        simpleItem(MWItems.SILVER_AXE.get());
        simpleItem(MWItems.SILVER_SHOVEL.get());
        simpleItem(MWItems.SILVER_HOE.get());
        simpleItem(MWItems.SILVER_SWORD.get());

        simpleItem(MWItems.FIRE_STARTER.get());

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
        hammer(MWItems.SILVER_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/silver_hammer"));
        hammer(MWItems.STEEL_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/steel_hammer"));
        hammer(MWItems.NETHERITE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/netherite_hammer"));

        forgeHammer(MWItems.STONE_FORGE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/stone_forge_hammer"));
        forgeHammer(MWItems.IRON_FORGE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_forge_hammer"));
        forgeHammer(MWItems.STEEL_FORGE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/steel_forge_hammer"));
        forgeHammer(MWItems.NETHERITE_FORGE_HAMMER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/netherite_forge_hammer"));

        longsword(MWItems.IRON_LONGSWORD.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_weapon"));
        longsword(MWItems.SILVER_LONGSWORD.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/silver_weapon"));
        longsword(MWItems.STEEL_LONGSWORD.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/steel_weapon"));
        longsword(MWItems.NETHERITE_LONGSWORD.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/netherite_weapon"));

        longAxe(MWItems.IRON_LONG_AXE.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_weapon"));
        longAxe(MWItems.SILVER_LONG_AXE.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/silver_weapon"));
        longAxe(MWItems.STEEL_LONG_AXE.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/steel_weapon"));
        longAxe(MWItems.NETHERITE_LONG_AXE.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/netherite_weapon"));

        dagger(MWItems.IRON_DAGGER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/iron_weapon"));
        dagger(MWItems.SILVER_DAGGER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/silver_weapon"));
        dagger(MWItems.STEEL_DAGGER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/steel_weapon"));
        dagger(MWItems.NETHERITE_DAGGER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/netherite_weapon"));

        withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(MWItems.ALLOY_FURNACE.get())).toString(),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/alloy_furnace"));

        MWItems.HEATED_INGOTS.forEach(
                (material, regOb) -> layeredTextureChildItem(regOb.get(), regOb.get().getBaseItem(), "redhot_ingot_layer")
        );

        MWItems.TOOL_PARTS.forEach(createPartModel());

        createWeaponPart("iron", "hammer_head", "iron_hammer", "heated_iron_hammer_head");
        createWeaponPart("iron", "hammer_head_raw", "iron_hammer", "heated_iron_hammer_head");
        createWeaponPart("iron", "long_axe_head", "iron_weapon", "heated_iron_weapon");
        createWeaponPart("iron", "longsword_blade", "iron_weapon", "heated_iron_weapon");

        createWeaponPart("steel", "hammer_head", "steel_hammer", "heated_steel_hammer_head");
        createWeaponPart("steel", "hammer_head_raw", "steel_hammer", "heated_steel_hammer_head");
        createWeaponPart("steel", "long_axe_head", "steel_weapon", "heated_steel_weapon");
        createWeaponPart("steel", "longsword_blade", "steel_weapon", "heated_steel_weapon");

        createWeaponPart("silver", "hammer_head", "silver_hammer", "heated_silver_hammer_head");
        createWeaponPart("silver", "hammer_head_raw", "silver_hammer", "heated_silver_hammer_head");
        createWeaponPart("silver", "long_axe_head", "silver_weapon", "heated_silver_weapon");
        createWeaponPart("silver", "longsword_blade", "silver_weapon", "heated_silver_weapon");

        createWeaponPart("netherite", "hammer_head", "netherite_hammer", "heated_netherite_hammer_head");
        createWeaponPart("netherite", "hammer_head_raw", "netherite_hammer", "heated_netherite_hammer_head");
        createWeaponPart("netherite", "long_axe_head", "netherite_weapon", "heated_netherite_weapon");
        createWeaponPart("netherite", "longsword_blade", "netherite_weapon", "heated_netherite_weapon");
    }

    private BiConsumer<? super String, ? super RegistryObject<Item>> createPartModel() {
        return (key, regOb) -> {
            if (key.startsWith("heated")) {
                String[] parts = key.split("_");
                String layer1 = parts[parts.length - 2] + "_" + parts[parts.length - 1];
                String layer0 = parts[parts.length - 3] + "_" + layer1;
                layeredTextureItem(regOb.get(), layer0, "redhot_" + layer1 + "_layer");
            } else {
                simpleItem(regOb.get());
            }
        };
    }

    private void createWeaponPart(String material, String partType, String texture, String heatedTexture) {
        withExistingParent(material + "_" + partType, modid + ":" + partType + "_template")
                .texture("0", modid + ":item/" + texture)
                .texture("particle", modid + ":item/" + texture);
        withExistingParent("heated_" + material + "_" + partType, modid + ":" + partType + "_template")
                .texture("0", modid + ":item/" + heatedTexture)
                .texture("particle", modid + ":item/" + heatedTexture);
    }

    @SuppressWarnings("UnusedReturnValue")
    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()));
    }

    private ItemModelBuilder dagger(Item daggerItem, ResourceLocation texture) {
        return withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(daggerItem)).toString(), new ResourceLocation(MedievalWorlds.MOD_ID, "item/dagger_template"))
                .texture("0", texture)
                .texture("particle", texture);
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

    private ItemModelBuilder layeredTextureChildItem(Item item, Item parent, String... textures) {
        ResourceLocation parentId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(parent));
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString(),"item/generated")
                .texture("layer0", new ResourceLocation(parentId.getNamespace(), "item/" + parentId.getPath()));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture("layer" + (i + 1), new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + textures[i]));
        }
        return builder;
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
