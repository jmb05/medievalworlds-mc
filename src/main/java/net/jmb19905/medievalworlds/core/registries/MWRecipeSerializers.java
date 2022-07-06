package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.alloy.AlloyRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.burn.BurnRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.burn.IBurnRecipe;
import net.jmb19905.medievalworlds.common.recipes.fletching.FletchingRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.common.recipes.forge.ForgeRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.forge.IForgeRecipe;
import net.jmb19905.medievalworlds.common.recipes.grind.GrindRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.grind.IGrindRecipe;
import net.jmb19905.medievalworlds.common.recipes.noRecipe.NoRecipe;
import net.jmb19905.medievalworlds.common.recipes.noRecipe.NoRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.slackTub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.recipes.slackTub.SlackTubRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.SmithingRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWRecipeSerializers {

    public static final RecipeType<IAlloyRecipe> ALLOY_TYPE = new CustomRecipeType<>(IAlloyRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IBloomRecipe> BLOOM_TYPE = new CustomRecipeType<>(IBloomRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IForgeRecipe> FORGE_TYPE = new CustomRecipeType<>(IForgeRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IAnvilRecipe> ANVIL_TYPE = new CustomRecipeType<>(IAnvilRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IGrindRecipe> GRIND_TYPE = new CustomRecipeType<>(IGrindRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IAlloyRecipe> BURN_TYPE = new CustomRecipeType<>(IAlloyRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<ISmithingRecipe> SMITHING_TYPE = new CustomRecipeType<>(ISmithingRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IFletchingRecipe> FLETCHING_TYPE = new CustomRecipeType<>(IFletchingRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<ISlackTubRecipe> SLACK_TUB_TYPE = new CustomRecipeType<>(ISlackTubRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<NoRecipe> NO_RECIPE_TYPE = new CustomRecipeType<>(NoRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZERS.register("alloy", AlloyRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BLOOM_SERIALIZER = RECIPE_SERIALIZERS.register("bloom", BloomRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FORGE_SERIALIZER = RECIPE_SERIALIZERS.register("forge", ForgeRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ANVIL_SERIALIZER = RECIPE_SERIALIZERS.register("anvil", AnvilRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> GRIND_SERIALIZER = RECIPE_SERIALIZERS.register("grind", GrindRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BURN_SERIALIZER = RECIPE_SERIALIZERS.register("burn", BurnRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SMITHING_SERIALIZER = RECIPE_SERIALIZERS.register("smithing", SmithingRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("fletching", FletchingRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SLACK_TUB_SERIALIZER = RECIPE_SERIALIZERS.register("slack_tub", SlackTubRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> NO_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("no_recipe", NoRecipeSerializer::new);

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, IAlloyRecipe.RECIPE_TYPE_ID, ALLOY_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IBloomRecipe.RECIPE_TYPE_ID, BLOOM_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IForgeRecipe.RECIPE_TYPE_ID, FORGE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IAnvilRecipe.RECIPE_TYPE_ID, ANVIL_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IGrindRecipe.RECIPE_TYPE_ID, GRIND_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IBurnRecipe.RECIPE_TYPE_ID, BURN_TYPE);
        Registry.register(Registry.RECIPE_TYPE, ISmithingRecipe.RECIPE_TYPE_ID, SMITHING_TYPE);
        Registry.register(Registry.RECIPE_TYPE, IFletchingRecipe.RECIPE_TYPE_ID, FLETCHING_TYPE);
        Registry.register(Registry.RECIPE_TYPE, ISlackTubRecipe.RECIPE_TYPE_ID, SLACK_TUB_TYPE);
        Registry.register(Registry.RECIPE_TYPE, NoRecipe.RECIPE_TYPE_ID, NO_RECIPE_TYPE);
    }

    private record CustomRecipeType<T extends Recipe<?>>(ResourceLocation id) implements RecipeType<T> {
        @Override
        public String toString() {
            return id.toString();
        }
    }
}