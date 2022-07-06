package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.common.recipes.slackTub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MedievalWorlds.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new FletchingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new AlloyRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new AnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new SlackTubRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new CustomSmithingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new BloomRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(new RecipeType<>(IFletchingRecipe.RECIPE_TYPE_ID, IFletchingRecipe.class), recipeManager.getAllRecipesFor(MWRecipeSerializers.FLETCHING_TYPE));
        registration.addRecipes(new RecipeType<>(IAlloyRecipe.RECIPE_TYPE_ID, IAlloyRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IAlloyRecipe).toList());
        registration.addRecipes(new RecipeType<>(IAnvilRecipe.RECIPE_TYPE_ID, IAnvilRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IAnvilRecipe).toList());
        registration.addRecipes(new RecipeType<>(ISlackTubRecipe.RECIPE_TYPE_ID, ISlackTubRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof ISlackTubRecipe).toList());
        registration.addRecipes(new RecipeType<>(ISmithingRecipe.RECIPE_TYPE_ID, ISmithingRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof ISmithingRecipe).toList());
        registration.addRecipes(new RecipeType<>(IBloomRecipe.RECIPE_TYPE_ID, IBloomRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IBloomRecipe).toList());
    }
}