package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWRecipeSerializers {

    public static final RecipeType<AnvilRecipe> ANVIL_TYPE = new CustomRecipeType<>(AnvilRecipe.ID);
    public static final RecipeType<AlloyRecipe> ALLOY_TYPE = new CustomRecipeType<>(AlloyRecipe.ID);
    public static final RecipeType<BloomRecipe> BLOOM_TYPE = new CustomRecipeType<>(BloomRecipe.ID);
    public static final RecipeType<BurnRecipe> BURN_TYPE = new CustomRecipeType<>(BurnRecipe.ID);
    public static final RecipeType<FletchingRecipe> FLETCHING_TYPE = new CustomRecipeType<>(FletchingRecipe.ID);
    public static final RecipeType<ScrapRecipe> SCRAP_TYPE = new CustomRecipeType<>(ScrapRecipe.ID);
    public static final RecipeType<SlackTubRecipe> SLACK_TUB_TYPE = new CustomRecipeType<>(SlackTubRecipe.ID);
    public static final RecipeType<SmithingRecipe> SMITHING_TYPE = new CustomRecipeType<>(SmithingRecipe.ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> ANVIL_SERIALIZER = RECIPE_SERIALIZER.register("anvil", AnvilRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZER.register("alloy", AlloyRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BLOOM_SERIALIZER = RECIPE_SERIALIZER.register("bloom", BloomRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BURN_SERIALIZER = RECIPE_SERIALIZER.register("burn", BurnRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FLETCHING_SERIALIZER = RECIPE_SERIALIZER.register("fletching", FletchingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SCRAP_SERIALIZER = RECIPE_SERIALIZER.register("scrap", ScrapRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SLACK_TUB_SERIALIZER = RECIPE_SERIALIZER.register("slack_tub", SlackTubRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SMITHING_SERIALIZER = RECIPE_SERIALIZER.register("smithing", SmithingRecipe.Serializer::new);

    public static final RegistryObject<SimpleRecipeSerializer<?>> SCRAP_ITEM_SERIALIZER = RECIPE_SERIALIZER.register("crafting_special_scrap_item", () -> new SimpleRecipeSerializer<>(ScrapItemRecipe::new));

    private record CustomRecipeType<T extends Recipe<?>>(ResourceLocation id) implements RecipeType<T> {
        @Override
        public String toString() {
            return id.toString();
        }
    }

}