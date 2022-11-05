package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum MWTiers implements Tier {

    STURDY_WOOD(0, 200, 2F, 0.5F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    BRONZE(1, 200, 6f, 1.5f, 10, () -> Ingredient.of(MWItems.BRONZE_INGOT.get())),
    SILVER(1, 150, 8f, 1.5f, 24, () -> Ingredient.of(MWItems.SILVER_INGOT.get())),
    STEEL(3, 1500, 8f, 3f, 12, () -> Ingredient.of(MWItems.STEEL_INGOT.get())),
    CUSTOM_STONE(0, 131, 4F, 1F, 5, () -> Ingredient.of(Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.BLACKSTONE)),
    CUSTOM_DIAMOND(3, 131, 8F, 3F, 10, () -> Ingredient.of(Items.DIAMOND));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    MWTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> ingredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = ingredient;
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
