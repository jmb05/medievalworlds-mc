package net.jmb19905.medievalworlds.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.recipes.AlloyRecipe;
import net.jmb19905.medievalworlds.recipes.AlloyRecipeSerializer;
import net.jmb19905.medievalworlds.recipes.IAlloyRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerRegistryHandler {

    public static final IRecipeSerializer<AlloyRecipe> ALLOY_RECIPE_SERIALIZER = new AlloyRecipeSerializer();
    public static final IRecipeType<IAlloyRecipe> ALLOY_TYPE = registerType(IAlloyRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> ALLOY_SERIALIZER = RECIPE_SERIALIZERS.register("alloy", () -> ALLOY_RECIPE_SERIALIZER);

    private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeID){
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeID, new RecipeType<>());
    }

    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T>{
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }

}
