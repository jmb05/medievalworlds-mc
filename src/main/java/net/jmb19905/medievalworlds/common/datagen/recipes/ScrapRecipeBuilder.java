package net.jmb19905.medievalworlds.common.datagen.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class ScrapRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final Item input;
    private final Item remainder;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ScrapRecipeBuilder(Item result, Item input, Item remainder) {
        this.result = result;
        this.input = input;
        this.remainder = remainder;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance criterion) {
        this.advancement.addCriterion(name, criterion);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String name) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        assert this.result.getItemCategory() != null;
        recipeConsumer.accept(new Result(id, result, input, remainder, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Item input;
        private final Item remainder;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, Item input, Item remainder, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.input = input;
            this.remainder = remainder;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.SCRAP_SERIALIZER.get();
        }

        @Override
        public @NotNull JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject object) {
            object.addProperty("output", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
            object.addProperty("input", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(input)).toString());
            object.addProperty("remainder", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(remainder)).toString());
        }
    }
}
