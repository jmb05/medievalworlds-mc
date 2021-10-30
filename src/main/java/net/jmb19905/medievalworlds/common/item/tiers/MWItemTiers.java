package net.jmb19905.medievalworlds.common.item.tiers;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum MWItemTiers implements Tier {

    BRONZE_ITEM_TIER(2, 200, 6f, 3f, 10, () -> {
        return Ingredient.of(MWItems.BRONZE_INGOT.get());
    }),
    COPPER_ITEM_TIER(1, 180, 5f, 3f, 8, () -> {
        return Ingredient.of(Items.COPPER_INGOT);
    }),
    DIAMOND_BLOCK_ITEM_TIER(3, 1750, 8f, 6f, 9, () -> {
        return Ingredient.of(Items.DIAMOND_BLOCK);
    }),
    IRON_BLOCK_ITEM_TIER(2, 1000, 6f, 3f, 8, () -> {
        return Ingredient.of(Items.IRON_BLOCK);
    }),
    NETHERITE_BLOCK_ITEM_TIER(4, 2500, 9f, 7f, 7, () -> {
        return Ingredient.of(Items.NETHERITE_BLOCK);
    }),
    SILVER_BLOCK_ITEM_TIER(2, 750, 7f, 4f, 8, () -> {
        return Ingredient.of(MWBlocks.SILVER_BLOCK_ITEM.get());
    }),
    SILVER_ITEM_TIER(2, 300, 6f, 4f, 22, () -> {
        return Ingredient.of(MWItems.SILVER_INGOT.get());
    }),
    STEEL_BLOCK_ITEM_TIER(2, 1500, 8f, 5f, 8, () -> {
        return Ingredient.of(MWBlocks.STEEL_BLOCK_ITEM.get());
    }),
    STEEL_ITEM_TIER(2, 1000, 8f, 4f, 10, () -> {
        return Ingredient.of(MWItems.STEEL_INGOT.get());
    }),
    TIN_ITEM_TIER(1, 150, 5f, 3f, 7, () -> {
        return Ingredient.of(MWItems.TIN_INGOT.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    MWItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient.get();
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}
