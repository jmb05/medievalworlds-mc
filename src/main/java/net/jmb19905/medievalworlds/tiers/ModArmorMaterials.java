package net.jmb19905.medievalworlds.tiers;

import net.jmb19905.medievalworlds.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.registries.ItemRegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum ModArmorMaterials implements IArmorMaterial {

    GAMBESON_MATERIAL("medievalworlds:gambeson",                new int[]{70, 90, 100, 80}, new int[]{2, 3, 3, 1}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0, () -> {
        return Ingredient.fromItems((IItemProvider) ItemTags.WOOL);
    }),

    COPPER_MATERIAL("medievalworlds:copper",                    new int[]{145, 200, 170, 150}, new int[]{2, 4, 4, 1}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.COPPER_INGOT.get());
    }),

    TIN_MATERIAL("medievalworlds:tin",                          new int[]{130, 180, 150, 140}, new int[]{2, 3, 4, 1}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.TIN_INGOT.get());
    }),

    BRONZE_MATERIAL("medievalworlds:bronze",                    new int[]{160, 230, 210, 180}, new int[]{2, 4, 5, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.BRONZE_INGOT.get());
    }),

    SILVER_MATERIAL("medievalworlds:silver",                    new int[]{170, 250, 230, 200}, new int[]{3, 6, 6, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1f, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.SILVER_INGOT.get());
    }),

    STEEL_MATERIAL("medievalworlds:steel",                      new int[]{300, 500, 500, 400}, new int[]{3, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.STEEL_INGOT.get());
    }),

    IRON_KNIGHT_MATERIAL("medievalworlds:iron_knight",          new int[]{363, 528, 495, 429}, new int[]{3, 6, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, () -> {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }),

    SILVER_KNIGHT_MATERIAL("medievalworlds:silver_knight",      new int[]{370, 530, 500, 430}, new int[]{3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.STEEL_INGOT.get());
    }),

    STEEL_KNIGHT_MATERIAL("medievalworlds:steel_knight",        new int[]{585, 810, 765, 675}, new int[]{3, 7, 9, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0, () -> {
        return Ingredient.fromItems(ItemRegistryHandler.STEEL_INGOT.get());
    }),

    DIAMOND_KNIGHT_MATERIAL("medievalworlds:diamond_knight",    new int[]{780, 1080, 1020, 900}, new int[]{4, 8, 10, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2, 0, () -> {
        return Ingredient.fromItems(Items.DIAMOND);
    }),

    WARRIOR_MATERIAL("medievalworlds:warrior",                  new int[]{1000, 1500, 1500, 1000}, new int[]{5, 9, 10, 4}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1, () -> {
        return Ingredient.fromItems(BlockRegistryHandler.STEEL_BLOCK_ITEM.get());
    });

    private final String name;
    private final int[] maxDamageArray;
    private final int [] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    ModArmorMaterials(String name, int[] maxDamageArray, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageArray = maxDamageArray;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return maxDamageArray[slotIn.getIndex()];
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
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

}
