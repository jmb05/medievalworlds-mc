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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class AlloyRecipeBuilder implements RecipeBuilder {

    private final ItemStack result;
    private final ItemStack input1;
    private final ItemStack input2;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public AlloyRecipeBuilder(ItemStack result, ItemStack input1, ItemStack input2) {
        this.result = result;
        this.input1 = input1;
        this.input2 = input2;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull ResourceLocation id) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        assert this.result.getItem().getItemCategory() != null;
        recipeConsumer.accept(new AlloyRecipeBuilder.Result(id, result, input1, input2, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                this.result.getItem().getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final ItemStack input1;
        private final ItemStack input2;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack result, ItemStack input1, ItemStack input2, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.input1 = input1;
            this.input2 = input2;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.ALLOY_SERIALIZER.get();
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
            serializeItemStack(result, "output", object);
            serializeItemStack(input1, "input1", object);
            serializeItemStack(input2, "input2", object);
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