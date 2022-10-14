package net.jmb19905.medievalworlds.common.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum MWTiers implements Tier {

    STURDY_WOOD(0, 200, 2.0F, 0.5F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    IRON_BLOCK_ITEM_TIER(2, 1000, 6f, 3f, 8, () -> Ingredient.of(Items.IRON_BLOCK));

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
