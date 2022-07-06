package net.jmb19905.medievalworlds.core.datagen.custom;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class SlackTubRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final int inputCount;
    private final ItemStack output;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public SlackTubRecipeBuilder(ItemStack output, Ingredient input, int inputCount) {
        this.input = input;
        this.output = output;
        this.inputCount = inputCount;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        assert this.output.getItem().getItemCategory() != null;
        recipeConsumer.accept(new Result(id, output, input, inputCount, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                this.output.getItem().getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack output;
        private final Ingredient input;
        private final int inputCount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack output, Ingredient input, int inputCount, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.output = output;
            this.input = input;
            this.inputCount = inputCount;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.SLACK_TUB_SERIALIZER.get();
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
            serializeOutput(output, object);
            object.add("input", input.toJson());
            object.addProperty("inputCount", inputCount);
        }

        private static void serializeOutput(ItemStack stack, JsonObject parent) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            if (stack.getCount() > 1) {
                jsonObject.addProperty("count", stack.getCount());
            }
            parent.add("output", jsonObject);
        }
    }
}
