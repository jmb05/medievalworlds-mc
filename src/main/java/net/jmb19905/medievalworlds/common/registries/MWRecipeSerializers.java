package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.alloy.AlloyRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.burn.BurnRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.burn.IBurnRecipe;
import net.jmb19905.medievalworlds.common.recipes.slacktub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.recipes.slacktub.SlackTubRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWRecipeSerializers {

    public static final RecipeType<IBurnRecipe> BURN_TYPE = new CustomRecipeType<>(IBurnRecipe.ID);
    public static final RecipeType<IAnvilRecipe> ANVIL_TYPE = new CustomRecipeType<>(IAnvilRecipe.ID);
    public static final RecipeType<IAlloyRecipe> ALLOY_TYPE = new CustomRecipeType<>(IAlloyRecipe.ID);
    public static final RecipeType<IBloomRecipe> BLOOM_TYPE = new CustomRecipeType<>(IBloomRecipe.ID);
    public static final RecipeType<ISlackTubRecipe> SLACK_TUB_TYPE = new CustomRecipeType<>(ISlackTubRecipe.ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> BURN_SERIALIZER = RECIPE_SERIALIZER.register("burn", BurnRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ANVIL_SERIALIZER = RECIPE_SERIALIZER.register("anvil", AnvilRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZER.register("alloy", AlloyRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BLOOM_SERIALIZER = RECIPE_SERIALIZER.register("bloom", BloomRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SLACK_TUB_SERIALIZER = RECIPE_SERIALIZER.register("slack_tub", SlackTubRecipeSerializer::new);

    private record CustomRecipeType<T extends Recipe<?>>(ResourceLocation id) implements RecipeType<T> {
        @Override
        public String toString() {
            return id.toString();
        }
    }

}