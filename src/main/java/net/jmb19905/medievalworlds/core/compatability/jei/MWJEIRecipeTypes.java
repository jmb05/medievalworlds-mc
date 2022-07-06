package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.alloy.IAlloyRecipe;
import net.jmb19905.medievalworlds.common.recipes.anvil.IAnvilRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.common.recipes.slackTub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;

public class MWJEIRecipeTypes {

    public static final RecipeType<IAlloyRecipe> ALLOY = RecipeType.create(MedievalWorlds.MOD_ID, "alloy", IAlloyRecipe.class);
    public static final RecipeType<IAnvilRecipe> ANVIL = RecipeType.create(MedievalWorlds.MOD_ID, "anvil", IAnvilRecipe.class);
    public static final RecipeType<ISmithingRecipe> SMITHING = RecipeType.create(MedievalWorlds.MOD_ID, "smithing", ISmithingRecipe.class);
    public static final RecipeType<IFletchingRecipe> FLETCHING = RecipeType.create(MedievalWorlds.MOD_ID, "fletching", IFletchingRecipe.class);
    public static final RecipeType<ISlackTubRecipe> SLACK_TUB = RecipeType.create(MedievalWorlds.MOD_ID, "slack_tub", ISlackTubRecipe.class);
    public static final RecipeType<IBloomRecipe> BLOOM = RecipeType.create(MedievalWorlds.MOD_ID, "bloom", IBloomRecipe.class);

}
