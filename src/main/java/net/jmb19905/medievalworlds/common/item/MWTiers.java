package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum MWTiers implements Tier {

    STURDY_WOOD(0, 200, 2.0F, 0.5F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    BRONZE_ITEM_TIER(1, 200, 6f, 3f, 10, () -> Ingredient.of(MWItems.BRONZE_INGOT.get())),
    SILVER_ITEM_TIER(2, 300, 6f, 4f, 22, () -> Ingredient.of(MWItems.SILVER_INGOT.get())),
    STEEL_ITEM_TIER(3, 1000, 8f, 4f, 10, () -> Ingredient.of(MWItems.STEEL_INGOT.get())),
    IRON_BLOCK_ITEM_TIER(2, 1000, 6f, 3f, 8, () -> Ingredient.of(Items.IRON_BLOCK)),
    NETHERITE_BLOCK_ITEM_TIER(4, 2500, 9f, 7f, 7, () -> Ingredient.of(Items.NETHERITE_BLOCK)),
    SILVER_BLOCK_ITEM_TIER(2, 750, 7f, 4f, 8, () -> Ingredient.of(MWItems.SILVER_BLOCK.get())),
    STEEL_BLOCK_ITEM_TIER(3, 1500, 8f, 5f, 8, () -> Ingredient.of(MWItems.STEEL_BLOCK.get()));


    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    @SuppressWarnings("deprecation")
    private final LazyLoadedValue<Ingredient> repairIngredient;

    @SuppressWarnings("deprecation")
    MWTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> ingredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(ingredient);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
