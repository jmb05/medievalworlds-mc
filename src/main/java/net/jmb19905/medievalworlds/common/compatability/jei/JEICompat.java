package net.jmb19905.medievalworlds.common.compatability.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.compatability.jei.categories.*;
import net.jmb19905.medievalworlds.common.recipes.*;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.VanillaOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MedievalWorlds.MOD_ID, MedievalWorlds.MOD_ID);
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new AlloyRecipeCategory(guiHelper),
                new AnvilRecipeCategory(guiHelper),
                new SlackTubRecipeCategory(guiHelper),
                new CustomSmithingRecipeCategory(guiHelper),
                new BloomRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(new RecipeType<>(AlloyRecipe.ID, AlloyRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof AlloyRecipe).toList());
        registration.addRecipes(new RecipeType<>(AnvilRecipe.ID, AnvilRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof AnvilRecipe).toList());
        registration.addRecipes(new RecipeType<>(SlackTubRecipe.ID, SlackTubRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof SlackTubRecipe).toList());
        registration.addRecipes(new RecipeType<>(SmithingRecipe.ID, SmithingRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof SmithingRecipe).toList());
        registration.addRecipes(new RecipeType<>(BloomRecipe.ID, BloomRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof BloomRecipe).toList());
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(MWBlocks.ALLOY_FURNACE.get()), MWJEIRecipeTypes.ALLOY);
        registration.addRecipeCatalyst(new ItemStack(VanillaOverride.ANVIL.get()), MWJEIRecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(VanillaOverride.CHIPPED_ANVIL.get()), MWJEIRecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(VanillaOverride.DAMAGED_ANVIL.get()), MWJEIRecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(MWBlocks.STEEL_ANVIL.get()), MWJEIRecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(MWBlocks.STONE_ANVIL.get()), MWJEIRecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(MWBlocks.SLACK_TUB.get()), MWJEIRecipeTypes.SLACK_TUB);
        registration.addRecipeCatalyst(new ItemStack(VanillaOverride.SMITHING_TABLE.get()), MWJEIRecipeTypes.SMITHING);
        registration.addRecipeCatalyst(new ItemStack(MWBlocks.SIMPLE_BLOOMERY.get()), MWJEIRecipeTypes.BLOOM);
    }
}