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
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.slacktub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
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
        registration.addRecipes(new RecipeType<>(IAlloyRecipe.ID, IAlloyRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IAlloyRecipe).toList());
        registration.addRecipes(new RecipeType<>(IAnvilRecipe.ID, IAnvilRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IAnvilRecipe).toList());
        registration.addRecipes(new RecipeType<>(ISlackTubRecipe.ID, ISlackTubRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof ISlackTubRecipe).toList());
        registration.addRecipes(new RecipeType<>(ISmithingRecipe.ID, ISmithingRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof ISmithingRecipe).toList());
        registration.addRecipes(new RecipeType<>(IBloomRecipe.ID, IBloomRecipe.class), recipeManager.getRecipes().stream().filter(rec -> rec instanceof IBloomRecipe).toList());
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