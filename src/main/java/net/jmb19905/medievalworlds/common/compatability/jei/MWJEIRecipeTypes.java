package net.jmb19905.medievalworlds.common.compatability.jei;

import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.*;

public class MWJEIRecipeTypes {

    public static final RecipeType<AlloyRecipe> ALLOY = RecipeType.create(MedievalWorlds.MOD_ID, "alloy", AlloyRecipe.class);
    public static final RecipeType<AnvilRecipe> ANVIL = RecipeType.create(MedievalWorlds.MOD_ID, "anvil", AnvilRecipe.class);
    public static final RecipeType<SmithingRecipe> SMITHING = RecipeType.create(MedievalWorlds.MOD_ID, "smithing", SmithingRecipe.class);
    public static final RecipeType<SlackTubRecipe> SLACK_TUB = RecipeType.create(MedievalWorlds.MOD_ID, "slack_tub", SlackTubRecipe.class);
    public static final RecipeType<BloomRecipe> BLOOM = RecipeType.create(MedievalWorlds.MOD_ID, "bloom", BloomRecipe.class);

}
