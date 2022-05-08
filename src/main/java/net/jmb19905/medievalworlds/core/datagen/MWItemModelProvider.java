package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public class MWItemModelProvider extends ItemModelProvider {

    public MWItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(MWItems.RAW_TIN.get());
        simpleItem(MWItems.TIN_INGOT.get());
        simpleItem(MWItems.TIN_NUGGET.get());

        simpleItem(MWItems.BRONZE_INGOT.get());
        simpleItem(MWItems.BRONZE_NUGGET.get());

        simpleItem(MWItems.STEEL_INGOT.get());
        simpleItem(MWItems.STEEL_NUGGET.get());

        simpleItem(MWItems.RAW_SILVER.get());
        simpleItem(MWItems.SILVER_INGOT.get());
        simpleItem(MWItems.SILVER_NUGGET.get());

        simpleItem(MWItems.RUBY.get());

        layeredTextureItem(MWItems.HEATED_TIN_INGOT.get(), "tin_ingot", "redhot_ingot_layer");
        layeredTextureItem(MWItems.HEATED_BRONZE_INGOT.get(), "bronze_ingot", "redhot_ingot_layer");
        layeredTextureItem(MWItems.HEATED_COPPER_INGOT.get(), new ResourceLocation("item/copper_ingot"), new ResourceLocation(MedievalWorlds.MOD_ID, "item/redhot_ingot_layer"));
        layeredTextureItem(MWItems.HEATED_IRON_INGOT.get(), new ResourceLocation("item/iron_ingot"), new ResourceLocation(MedievalWorlds.MOD_ID, "item/redhot_ingot_layer"));
        layeredTextureItem(MWItems.HEATED_STEEL_INGOT.get(), "steel_ingot", "redhot_ingot_layer");
        layeredTextureItem(MWItems.HEATED_SILVER_INGOT.get(), "silver_ingot", "redhot_ingot_layer");
        layeredTextureItem(MWItems.HEATED_GOLD_INGOT.get(), new ResourceLocation("item/gold_ingot"), new ResourceLocation(MedievalWorlds.MOD_ID, "item/redhot_ingot_layer"));
        layeredTextureItem(MWItems.HEATED_NETHERITE_INGOT.get(), new ResourceLocation("item/netherite_ingot"), new ResourceLocation(MedievalWorlds.MOD_ID, "item/redhot_ingot_layer"));

        for(String id : MWItems.TOOL_PARTS.keySet()) {
            RegistryObject<Item> object = MWItems.TOOL_PARTS.get(id);
            String[] parts = id.split("_");
            if(parts[0].equals("heated")) {
                String layer1 = parts[parts.length - 2] + "_" + parts[parts.length - 1];
                String layer0 = parts[parts.length - 3] + "_" + layer1;
                layeredTextureItem(object.get(), layer0, layer1);
            } else {
                simpleItem(object.get());
            }
        }

        handheldItem(MWItems.BRONZE_AXE.get());
        handheldItem(MWItems.BRONZE_HOE.get());
        handheldItem(MWItems.BRONZE_PICKAXE.get());
        handheldItem(MWItems.BRONZE_SHOVEL.get());
        handheldItem(MWItems.BRONZE_SWORD.get());

        handheldItem(MWItems.STEEL_AXE.get());
        handheldItem(MWItems.STEEL_HOE.get());
        handheldItem(MWItems.STEEL_PICKAXE.get());
        handheldItem(MWItems.STEEL_SHOVEL.get());
        handheldItem(MWItems.STEEL_SWORD.get());

        handheldItem(MWItems.SILVER_AXE.get());
        handheldItem(MWItems.SILVER_HOE.get());
        handheldItem(MWItems.SILVER_PICKAXE.get());
        handheldItem(MWItems.SILVER_SHOVEL.get());
        handheldItem(MWItems.SILVER_SWORD.get());

        handheldItem(MWItems.FIRE_STARTER.get());

        simpleItem(MWItems.BRONZE_HELMET.get());
        simpleItem(MWItems.BRONZE_CHESTPLATE.get());
        simpleItem(MWItems.BRONZE_LEGGINGS.get());
        simpleItem(MWItems.BRONZE_BOOTS.get());

        simpleItem(MWItems.STEEL_HELMET.get());
        simpleItem(MWItems.STEEL_CHESTPLATE.get());
        simpleItem(MWItems.STEEL_LEGGINGS.get());
        simpleItem(MWItems.STEEL_BOOTS.get());

        simpleItem(MWItems.SILVER_HELMET.get());
        simpleItem(MWItems.SILVER_CHESTPLATE.get());
        simpleItem(MWItems.SILVER_LEGGINGS.get());
        simpleItem(MWItems.SILVER_BOOTS.get());

        simpleItem(MWItems.WARRIOR_HELMET.get());
        simpleItem(MWItems.WARRIOR_CHESTPLATE.get());
        simpleItem(MWItems.WARRIOR_LEGGINGS.get());
        simpleItem(MWItems.WARRIOR_BOOTS.get());

        simpleItem(MWItems.IRON_KNIGHT_HELMET.get());
        simpleItem(MWItems.IRON_KNIGHT_CHESTPLATE.get());
        simpleItem(MWItems.IRON_KNIGHT_LEGGINGS.get());
        simpleItem(MWItems.IRON_KNIGHT_BOOTS.get());

        simpleItem(MWItems.STEEL_KNIGHT_HELMET.get());
        simpleItem(MWItems.STEEL_KNIGHT_CHESTPLATE.get());
        simpleItem(MWItems.STEEL_KNIGHT_LEGGINGS.get());
        simpleItem(MWItems.STEEL_KNIGHT_BOOTS.get());

        simpleItem(MWItems.SILVER_KNIGHT_HELMET.get());
        simpleItem(MWItems.SILVER_KNIGHT_CHESTPLATE.get());
        simpleItem(MWItems.SILVER_KNIGHT_LEGGINGS.get());
        simpleItem(MWItems.SILVER_KNIGHT_BOOTS.get());

        simpleItem(MWItems.NETHERITE_KNIGHT_HELMET.get());
        simpleItem(MWItems.NETHERITE_KNIGHT_CHESTPLATE.get());
        simpleItem(MWItems.NETHERITE_KNIGHT_LEGGINGS.get());
        simpleItem(MWItems.NETHERITE_KNIGHT_BOOTS.get());

        simpleItem(MWItems.GAMBESON_CHESTPLATE.get());

        simpleItem(MWItems.SILVER_HORSE_ARMOR.get());
        simpleItem(MWItems.STEEL_HORSE_ARMOR.get());
        simpleItem(MWItems.NETHERITE_HORSE_ARMOR.get());

        simpleItem(MWItems.CROWN.get());
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(Objects.requireNonNull(item.getRegistryName()).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder layeredTextureItem(Item item, String... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(item.getRegistryName()).getPath(), new ResourceLocation("item/generated"));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture("layer" + i, new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + textures[i]));
        }
        return builder;
    }

    private ItemModelBuilder layeredTextureItem(Item item, ResourceLocation... textures) {
        ItemModelBuilder builder = withExistingParent(Objects.requireNonNull(item.getRegistryName()).getPath(), new ResourceLocation("item/generated"));
        for(int i=0;i<textures.length;i++) {
            builder = builder.texture("layer" + i, textures[i]);
        }
        return builder;
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(Objects.requireNonNull(item.getRegistryName()).getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MedievalWorlds.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

}
