package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RecipeSerializerRegistryHandler {

    public static final RecipeSerializer<AlloyRecipe> ALLOY_RECIPE_SERIALIZER = new AlloyRecipeSerializer();
    public static final RecipeType<IAlloyRecipe> ALLOY_TYPE = registerType(IAlloyRecipe.RECIPE_TYPE_ID);

    public static final RecipeSerializer<BloomRecipe> BLOOM_RECIPE_SERIALIZER = new BloomRecipeSerializer();
    public static final RecipeType<IBloomRecipe> BLOOM_TYPE = registerType(IBloomRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZERS.register("alloy", () -> ALLOY_RECIPE_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> BLOOM_SERIALIZER = RECIPE_SERIALIZERS.register("bloom", () -> BLOOM_RECIPE_SERIALIZER);

    @SuppressWarnings("unchecked")
    private static <R extends RecipeType<?>> R registerType(ResourceLocation recipeTypeID){
        return (R) Registry.register(Registry.RECIPE_TYPE, recipeTypeID, new CustomRecipeType<>());
    }

    private static class CustomRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
        }
    }

}