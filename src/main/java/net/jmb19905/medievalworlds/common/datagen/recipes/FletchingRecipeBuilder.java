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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class FletchingRecipeBuilder implements RecipeBuilder {

    private final ItemStack result;
    private final Ingredient arrowHead;
    private final Ingredient arrowShaft;
    private final Ingredient arrowFletching;

    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public FletchingRecipeBuilder(ItemStack result, Ingredient arrowHead, Ingredient arrowShaft, Ingredient arrowFletching) {
        this.result = result;
        this.arrowHead = arrowHead;
        this.arrowShaft = arrowShaft;
        this.arrowFletching = arrowFletching;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
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
        recipeConsumer.accept(new Result(id, result, arrowHead, arrowShaft, arrowFletching, advancement, new ResourceLocation(id.getNamespace(), "recipes/" +
                this.result.getItem().getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    public static final class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final Ingredient arrowHead;
        private final Ingredient arrowShaft;
        private final Ingredient arrowFletching;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack result, Ingredient arrowHead, Ingredient arrowShaft, Ingredient arrowFletching, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.arrowHead = arrowHead;
            this.arrowShaft = arrowShaft;
            this.arrowFletching = arrowFletching;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            jsonObject.add("head", arrowHead.toJson());
            jsonObject.add("shaft", arrowShaft.toJson());
            jsonObject.add("fletching", arrowFletching.toJson());
            JsonObject stackObject = new JsonObject();
            stackObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem())).toString());
            if (result.getCount() > 1) {
                stackObject.addProperty("count", result.getCount());
            }
            jsonObject.add("result", stackObject);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MWRecipeSerializers.FLETCHING_SERIALIZER.get();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}