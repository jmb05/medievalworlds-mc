package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.fletching.FletchingRecipe;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MedievalWorlds.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FletchingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<IFletchingRecipe> recipes = recipeManager.getAllRecipesFor(MWRecipeSerializers.FLETCHING_TYPE);
        registration.addRecipes(new RecipeType<>(IFletchingRecipe.RECIPE_TYPE_ID, IFletchingRecipe.class), recipes);
    }
}
