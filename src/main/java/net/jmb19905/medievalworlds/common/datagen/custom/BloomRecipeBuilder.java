package net.jmb19905.medievalworlds.common.datagen.custom;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class BloomRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final ItemStack primaryOutput;
    private final ItemStack primaryPacked;
    private final int primaryOffset;
    private final ItemStack secondaryOutput;
    private final ItemStack secondaryPacked;
    private final int secondaryOffset;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public BloomRecipeBuilder(Ingredient input, ItemLike primaryOutput, int primaryOutCount, ItemLike primaryPacked, int primaryPackedCount, int primaryOffset, ItemLike secondaryOutput, int secondaryOutCount, ItemLike secondaryPacked, int secondaryPackedCount, int secondaryOffset) {
        this.input = input;
        this.primaryOutput = new ItemStack(primaryOutput, primaryOutCount);
        this.primaryPacked = new ItemStack(primaryPacked, primaryPackedCount);
        this.primaryOffset = primaryOffset;
        this.secondaryOutput = new ItemStack(secondaryOutput, secondaryOutCount);
        this.secondaryPacked = new ItemStack(secondaryPacked, secondaryPackedCount);
        this.secondaryOffset = secondaryOffset;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String name) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return primaryOutput.getItem();
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        recipeConsumer.accept(new BloomRecipeBuilder.Result(id, input, primaryOutput, primaryPacked, primaryOffset, secondaryOutput, secondaryPacked, secondaryOffset, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                Objects.requireNonNull(this.primaryOutput.getItem().getItemCategory()).getRecipeFolderName() + "/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack primaryOutput;
        private final ItemStack primaryPacked;
        private final int primaryOffset;
        private final ItemStack secondaryOutput;
        private final ItemStack secondaryPacked;
        private final int secondaryOffset;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient input, ItemStack primaryOutput, ItemStack primaryPacked, int primaryOffset, ItemStack secondaryOutput, ItemStack secondaryPacked, int secondaryOffset, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.input = input;
            this.primaryOutput = primaryOutput;
            this.primaryPacked = primaryPacked;
            this.primaryOffset = primaryOffset;
            this.secondaryOutput = secondaryOutput;
            this.secondaryPacked = secondaryPacked;
            this.secondaryOffset = secondaryOffset;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.BLOOM_SERIALIZER.get();
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
            object.add("input", input.toJson());
            serializeItemStack(primaryOutput, "primaryOutput", object);
            serializeItemStack(primaryPacked, "primaryPacked", object);
            object.addProperty("primaryOffset", primaryOffset);
            serializeItemStack(secondaryOutput, "secondaryOutput", object);
            serializeItemStack(secondaryPacked, "secondaryPacked", object);
            object.addProperty("secondaryOffset", secondaryOffset);
        }

        private static void serializeItemStack(ItemStack stack, String name, JsonObject parent) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString());
            if (stack.getCount() > 1) {
                jsonObject.addProperty("count", stack.getCount());
            }
            parent.add(name, jsonObject);
        }
    }
}