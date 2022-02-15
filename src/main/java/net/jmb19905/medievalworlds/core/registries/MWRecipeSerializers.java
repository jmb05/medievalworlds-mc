package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.alloy.AlloyRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.burn.BurnRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.forge.ForgeRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.forge.IForgeRecipe;
import net.jmb19905.medievalworlds.common.recipes.grind.GrindRecipeSerializer;
import net.jmb19905.medievalworlds.common.recipes.grind.IGrindRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.SmithingRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MWRecipeSerializers {

    public static final RecipeType<IAlloyRecipe> ALLOY_TYPE = registerType(IAlloyRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IBloomRecipe> BLOOM_TYPE = registerType(IBloomRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IForgeRecipe> FORGE_TYPE = registerType(IForgeRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IAnvilRecipe> ANVIL_TYPE = registerType(IAnvilRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IGrindRecipe> GRIND_TYPE = registerType(IGrindRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<IAlloyRecipe> BURN_TYPE = registerType(IAlloyRecipe.RECIPE_TYPE_ID);
    public static final RecipeType<ISmithingRecipe> SMITHING_TYPE = registerType(ISmithingRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZERS.register("alloy", AlloyRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BLOOM_SERIALIZER = RECIPE_SERIALIZERS.register("bloom", BloomRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FORGE_SERIALIZER = RECIPE_SERIALIZERS.register("forge", ForgeRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ANVIL_SERIALIZER = RECIPE_SERIALIZERS.register("anvil", AnvilRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> GRIND_SERIALIZER = RECIPE_SERIALIZERS.register("grind", GrindRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> BURN_SERIALIZER = RECIPE_SERIALIZERS.register("burn", BurnRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SMITHING_SERIALIZER = RECIPE_SERIALIZERS.register("smithing", SmithingRecipeSerializer::new);

    @SuppressWarnings("unchecked")
    private static <R extends RecipeType<?>> R registerType(ResourceLocation recipeTypeID){
        return (R) Registry.register(Registry.RECIPE_TYPE, recipeTypeID, new CustomRecipeType<>(recipeTypeID.getPath()));
    }

    private record CustomRecipeType<T extends Recipe<?>>(String id) implements RecipeType<T> {
        @Override
        public String toString() {
            return MedievalWorlds.MOD_ID + ":" + id;
        }
    }

}