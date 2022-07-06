package net.jmb19905.medievalworlds.core.datagen.custom;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NoRecipeBuilder implements RecipeBuilder {

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance trigger) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        recipeConsumer.accept(new NoRecipeBuilder.Result(id));
    }

    @SuppressWarnings("ClassCanBeRecord")
    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.NO_RECIPE_SERIALIZER.get();
        }

        @Override
        public @NotNull JsonObject serializeAdvancement() {
            Advancement.Builder advancement = Advancement.Builder.advancement();
            return advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return new ResourceLocation("nothing");
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject object) {}
    }
}