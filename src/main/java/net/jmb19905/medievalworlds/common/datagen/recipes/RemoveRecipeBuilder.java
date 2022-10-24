package net.jmb19905.medievalworlds.common.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class RemoveRecipeBuilder implements RecipeBuilder {

    private final ItemLike removedRecipeResult;

    public RemoveRecipeBuilder(ItemLike removedRecipeResult) {
        this.removedRecipeResult = removedRecipeResult;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance triggerInstance) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@org.jetbrains.annotations.Nullable String group) {
        return this;
    }

    public @NotNull Item getResult() {
        return Items.BARRIER;
    }

    public void save(Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        recipeConsumer.accept(new RemoveRecipeBuilder.Result(id, removedRecipeResult));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemLike removedRecipeResult;

        public Result(ResourceLocation id, ItemLike removedRecipeResult) {
            this.id = id;
            this.removedRecipeResult = removedRecipeResult;
        }

        public void serializeRecipeData(JsonObject jsonObject) {
            JsonArray jsonarray = new JsonArray();
            jsonarray.add(Ingredient.of(Items.BARRIER).toJson());
            jsonObject.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", "minecraft:barrier");
            jsonObject.add("result", jsonobject);
        }

        public @NotNull RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPELESS_RECIPE;
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public JsonObject serializeAdvancement() {
            Advancement.Builder advancement = Advancement.Builder.advancement();
            advancement.addCriterion("impossible", new ImpossibleTrigger.TriggerInstance());
            return advancement.serializeToJson();
        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            if (removedRecipeResult.asItem().getItemCategory() == null) return null;
            return new ResourceLocation(id.getNamespace(), "recipes/" + Objects.requireNonNull(removedRecipeResult.asItem().getItemCategory()).getRecipeFolderName() + "/" + id.getPath());
        }
    }
}
