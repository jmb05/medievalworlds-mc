package net.jmb19905.medievalworlds.common.item.tiers;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum MWArmorMaterials implements ArmorMaterial {

    GAMBESON_MATERIAL("medievalworlds:gambeson",                new int[]{70, 90, 100, 80}, new int[]{2, 3, 3, 1}, 7, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0, () -> {
        return Ingredient.of(Items.WHITE_WOOL)
                //Ingredient.of(ItemTags.WOOL)
                ;
    }),

    COPPER_MATERIAL("medievalworlds:copper",                    new int[]{145, 200, 170, 150}, new int[]{2, 4, 4, 1}, 7, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.of(Items.COPPER_INGOT);
    }),

    TIN_MATERIAL("medievalworlds:tin",                          new int[]{130, 180, 150, 140}, new int[]{2, 3, 4, 1}, 7, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.of(MWItems.TIN_INGOT.get());
    }),

    BRONZE_MATERIAL("medievalworlds:bronze",                    new int[]{160, 230, 210, 180}, new int[]{2, 4, 5, 1}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.of(MWItems.BRONZE_INGOT.get());
    }),

    SILVER_MATERIAL("medievalworlds:silver",                    new int[]{170, 250, 230, 200}, new int[]{3, 6, 6, 2}, 20, SoundEvents.ARMOR_EQUIP_IRON, 0.1f, 0, () -> {
        return Ingredient.of(MWItems.SILVER_INGOT.get());
    }),

    STEEL_MATERIAL("medievalworlds:steel",                      new int[]{300, 500, 500, 400}, new int[]{3, 5, 7, 2}, 10, SoundEvents.ARMOR_EQUIP_IRON, 0.5f, 0, () -> {
        return Ingredient.of(MWItems.STEEL_INGOT.get());
    }),

    IRON_KNIGHT_MATERIAL("medievalworlds:iron_knight",          new int[]{363, 528, 495, 429}, new int[]{3, 6, 7, 3}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0, 0, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),

    SILVER_KNIGHT_MATERIAL("medievalworlds:silver_knight",      new int[]{370, 530, 500, 430}, new int[]{3, 6, 8, 3}, 20, SoundEvents.ARMOR_EQUIP_IRON, 1, 0, () -> {
        return Ingredient.of(MWItems.STEEL_INGOT.get());
    }),

    STEEL_KNIGHT_MATERIAL("medievalworlds:steel_knight",        new int[]{585, 810, 765, 675}, new int[]{3, 7, 9, 3}, 10, SoundEvents.ARMOR_EQUIP_IRON, 1, 0, () -> {
        return Ingredient.of(MWItems.STEEL_INGOT.get());
    }),

    DIAMOND_KNIGHT_MATERIAL("medievalworlds:diamond_knight",    new int[]{780, 1080, 1020, 900}, new int[]{4, 8, 10, 4}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2, 0, () -> {
        return Ingredient.of(Items.DIAMOND);
    }),

    NETHERITE_KNIGHT_MATERIAL("medievalworlds:netherite_knight",    new int[]{810, 1100, 1150, 1000}, new int[]{4, 8, 10, 4}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3, .15f, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),

    WARRIOR_MATERIAL("medievalworlds:warrior",                  new int[]{1000, 1500, 1500, 1000}, new int[]{5, 9, 10, 4}, 7, SoundEvents.ARMOR_EQUIP_IRON, 3.5f, .2f, () -> {
        return Ingredient.of(MWBlocks.STEEL_BLOCK_ITEM.get());
    });

    private final String name;
    private final int[] maxDamageArray;
    private final int [] damageReductionAmountArray;
    private final int enchantmentValue;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairMaterial;

    MWArmorMaterials(String name, int[] maxDamageArray, int[] damageReductionAmountArray, int enchantmentValue, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageArray = maxDamageArray;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantmentValue = enchantmentValue;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = repairMaterial.get();
    }
    
    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return maxDamageArray[slot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return damageReductionAmountArray[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial;
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

}
