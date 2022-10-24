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
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

public class BurnRecipeBuilder implements RecipeBuilder {

    private final String input;
    private final String output;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public BurnRecipeBuilder(String output, String input) {
        this.input = input;
        this.output = output;
    }

    @Override
    public @Nonnull RecipeBuilder unlockedBy(@Nonnull String name, @Nonnull CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public @Nonnull RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @Nonnull Item getResult() {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(output)));
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> recipeConsumer, @Nonnull ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        recipeConsumer.accept(new BurnRecipeBuilder.Result(id, output, input, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                Objects.requireNonNull(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(output))).getItemCategory()).getRecipeFolderName() + "/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String output;
        private final String input;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, String output, String input, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.output = output;
            this.input = input;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public @Nonnull ResourceLocation getId() {
            return id;
        }

        @Override
        public @Nonnull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.BURN_SERIALIZER.get();
        }

        @Override
        public @Nonnull JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }

        @Override
        public void serializeRecipeData(@Nonnull JsonObject object) {
            object.addProperty("output", output);
            object.addProperty("input", input);
        }
    }
}
