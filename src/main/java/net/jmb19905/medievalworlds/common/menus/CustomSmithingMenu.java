package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.block.smithing.CustomSmithingTable;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.SmithingRecipe;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomSmithingMenu extends ItemCombinerMenu {

    private final Level level;
    @Nullable
    private Recipe<Container> selectedRecipe;
    private final List<Recipe<Container>> recipes;

    public static final int MAX_NAME_LENGTH = 50;
    public int repairItemCountCost;
    private String itemName;
    private final DataSlot cost = DataSlot.standalone();

    public CustomSmithingMenu(int windowId, Inventory inventory, ContainerLevelAccess access) {
        super(MWMenuTypes.SMITHING_MENU.get(), windowId, inventory, access);
        this.level = inventory.player.level;
        this.recipes = new ArrayList<>();
        recipes.addAll(this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING));
        recipes.addAll(this.level.getRecipeManager().getAllRecipesFor(MWRecipeSerializers.SMITHING_TYPE));
        this.addDataSlot(this.cost);
    }

    public CustomSmithingMenu(int windowId, Inventory inventory) {
        this(windowId, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.getBlock() instanceof CustomSmithingTable;
    }

    @Override
    protected boolean mayPickup(@NotNull Player player, boolean b) {
        return (selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level)) || ((player.getAbilities().instabuild || player.experienceLevel >= this.cost.get()) && this.cost.get() > 0);
    }

    @Override
    protected void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        if(selectedRecipe != null) {
            stack.onCraftedBy(player.level, player, stack.getCount());
            this.resultSlots.awardUsedRecipes(player);
            int shrinkAmountInput = 1;
            int shrinkAmountAddition = 1;
            if (selectedRecipe instanceof SmithingRecipe) {
                shrinkAmountInput = ((SmithingRecipe) selectedRecipe).getInput().getCount();
                shrinkAmountAddition = ((SmithingRecipe) selectedRecipe).getAddition().getCount();
            }
            this.shrinkStackInSlot(0, shrinkAmountInput);
            this.shrinkStackInSlot(1, shrinkAmountAddition);
        }else {
            if (!player.getAbilities().instabuild)
                player.giveExperienceLevels(-this.cost.get());

            System.out.println(this.repairItemCountCost);
            this.inputSlots.setItem(0, ItemStack.EMPTY);
            if (this.repairItemCountCost > 0) {
                ItemStack itemstack = this.inputSlots.getItem(1);
                if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                    itemstack.shrink(this.repairItemCountCost);
                    this.inputSlots.setItem(1, itemstack);
                } else this.inputSlots.setItem(1, ItemStack.EMPTY);
            } else this.inputSlots.setItem(1, ItemStack.EMPTY);

            this.cost.set(0);
            System.out.println("Repaired item");
        }
        this.access.execute((level, pos) -> level.levelEvent(1044, pos, 0));
    }

    private void shrinkStackInSlot(int slot, int amount) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(amount);
        this.inputSlots.setItem(slot, itemstack);
    }

    @Override
    public void createResult() {
        List<UpgradeRecipe> listUpgrade = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);
        List<ISmithingRecipe> listSmithing = this.level.getRecipeManager().getRecipesFor(MWRecipeSerializers.SMITHING_TYPE, this.inputSlots, this.level);
        List<Recipe<Container>> list = new ArrayList<>();
        list.addAll(listSmithing);
        list.addAll(listUpgrade);
        if (list.isEmpty()) {
            createRepairResult();
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }
    }

    //Mostly copied from AnvilMenu#createResult
    private void createRepairResult() {
        ItemStack itemstack = this.inputSlots.getItem(0);
        this.cost.set(1);
        int i = 0;
        int j = 0;
        int k = 0;
        if (itemstack.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.cost.set(0);
        } else {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = this.inputSlots.getItem(1);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            j += itemstack.getBaseRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getBaseRepairCost());
            this.repairItemCountCost = 0;
            boolean flag = false;

            if (!itemstack2.isEmpty()) {
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemstack2).isEmpty();
                if (itemstack1.isDamageableItem() && itemstack1.getItem().isValidRepairItem(itemstack, itemstack2)) {
                    int l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
                    if (l2 <= 0) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.cost.set(0);
                        return;
                    }

                    int i3;
                    for(i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3) {
                        int j3 = itemstack1.getDamageValue() - l2;
                        itemstack1.setDamageValue(j3);
                        ++i;
                        l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
                    }

                    this.repairItemCountCost = i3;
                } else {
                    if (!flag && (!itemstack1.is(itemstack2.getItem()) || !itemstack1.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.cost.set(0);
                        return;
                    }

                    if (itemstack1.isDamageableItem() && !flag) {
                        int l = itemstack.getMaxDamage() - itemstack.getDamageValue();
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getDamageValue();
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;
                        if (l1 < 0) {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getDamageValue()) {
                            itemstack1.setDamageValue(l1);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    boolean flag2 = false;
                    boolean flag3 = false;

                    for(Enchantment enchantment1 : map1.keySet()) {
                        if (enchantment1 != null) {
                            int i2 = map.getOrDefault(enchantment1, 0);
                            int j2 = map1.get(enchantment1);
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                            boolean flag1 = enchantment1.canEnchant(itemstack);
                            if (this.player.getAbilities().instabuild || itemstack.is(Items.ENCHANTED_BOOK)) {
                                flag1 = true;
                            }

                            for(Enchantment enchantment : map.keySet()) {
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                                    flag1 = false;
                                    ++i;
                                }
                            }

                            if (!flag1) {
                                flag3 = true;
                            } else {
                                flag2 = true;
                                if (j2 > enchantment1.getMaxLevel()) {
                                    j2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, j2);
                                int k3 = switch (enchantment1.getRarity()) {
                                    case COMMON -> 1;
                                    case UNCOMMON -> 2;
                                    case RARE -> 4;
                                    case VERY_RARE -> 8;
                                };

                                if (flag) {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j2;
                                if (itemstack.getCount() > 1) {
                                    i = 40;
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.cost.set(0);
                        return;
                    }
                }
            }

            if (StringUtils.isBlank(this.itemName)) {
                if (itemstack.hasCustomHoverName()) {
                    k = 1;
                    i += k;
                    itemstack1.resetHoverName();
                }
            } else if (!this.itemName.equals(itemstack.getHoverName().getString())) {
                k = 1;
                i += k;
                itemstack1.setHoverName(new TextComponent(this.itemName));
            }
            if (flag && !itemstack1.isBookEnchantable(itemstack2)) itemstack1 = ItemStack.EMPTY;

            this.cost.set(j + i);
            if (i <= 0) {
                itemstack1 = ItemStack.EMPTY;
            }

            if (k == i && k > 0 && this.cost.get() >= 40) {
                this.cost.set(39);
            }

            if (this.cost.get() >= 40 && !this.player.getAbilities().instabuild) {
                itemstack1 = ItemStack.EMPTY;
            }

            if (!itemstack1.isEmpty()) {
                int k2 = itemstack1.getBaseRepairCost();
                if (!itemstack2.isEmpty() && k2 < itemstack2.getBaseRepairCost()) {
                    k2 = itemstack2.getBaseRepairCost();
                }

                if (k != i || k == 0) {
                    k2 = calculateIncreasedRepairCost(k2);
                }

                itemstack1.setRepairCost(k2);
                EnchantmentHelper.setEnchantments(map, itemstack1);
            }

            this.resultSlots.setItem(0, itemstack1);
            this.broadcastChanges();
        }
    }

    public static int calculateIncreasedRepairCost(int c) {
        return c * 2 + 1;
    }

    protected boolean shouldQuickMoveToAdditionalSlot(@NotNull ItemStack stack) {
        return this.recipes.stream().anyMatch((recipe) -> {
            if(recipe instanceof UpgradeRecipe) {
                return ((UpgradeRecipe) recipe).isAdditionIngredient(stack);
            }else if(recipe instanceof ISmithingRecipe) {
                return ((ISmithingRecipe) recipe).isAdditionIngredient(stack);
            }
            return false;
        });
    }

    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public void setItemName(String name) {
        this.itemName = name;
        if (this.getSlot(2).hasItem()) {
            ItemStack itemstack = this.getSlot(2).getItem();
            if (StringUtils.isBlank(name)) {
                itemstack.resetHoverName();
            } else {
                itemstack.setHoverName(new TextComponent(this.itemName));
            }
        }

        this.createRepairResult();
    }

    public int getCost() {
        return this.cost.get();
    }

    public void setMaximumCost(int value) {
        this.cost.set(value);
    }

}
