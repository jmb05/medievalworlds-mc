package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.block.CustomSmithingTable;
import net.jmb19905.medievalworlds.common.blockentities.SmithingTableBlockEntity;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.common.recipes.smithing.SmithingRecipe;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.jmb19905.medievalworlds.util.slots.InputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomSmithingMenu extends AbstractContainerMenu {

    private final Level level;
    private final ContainerLevelAccess access;
    @Nullable
    private Recipe<?> selectedRecipe;
    private final List<Recipe<?>> allRecipes;

    private final Player player;

    private final InputSlot baseSlot;
    private final InputSlot additionSlot;
    private final OutputSlot outputSlot;

    private final SmithingTableBlockEntity blockEntity;

    public int repairItemCountCost;
    private String itemName;
    private final DataSlot cost = DataSlot.standalone();
    private final DataSlot smithing = DataSlot.standalone();

    public CustomSmithingMenu(int windowId, Inventory inventory, SmithingTableBlockEntity blockEntity) {
        super(MWMenuTypes.SMITHING_MENU.get(), windowId);
        this.level = inventory.player.level;
        this.player = inventory.player;
        this.access = ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos());
        this.allRecipes = new ArrayList<>();
        allRecipes.addAll(this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING));
        allRecipes.addAll(this.level.getRecipeManager().getAllRecipesFor(MWRecipeSerializers.SMITHING_TYPE));
        setSmithing(false);

        this.blockEntity = blockEntity;

        baseSlot = new InputSlot(blockEntity.getInventory(), 0, 27, 47);
        this.addSlot(baseSlot);
        additionSlot = new InputSlot(blockEntity.getInventory(), 1, 76, 47);
        this.addSlot(additionSlot);
        outputSlot = new OutputSlot(blockEntity.getInventory(), 2, 134, 47) {
            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                CustomSmithingMenu.this.onTake(player, stack);
                setSmithing(false);
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return ((selectedRecipe != null && matchesSelected(level)) && (player.getAbilities().instabuild || player.experienceLevel >= cost.get())) || ((player.getAbilities().instabuild || player.experienceLevel >= cost.get()) && cost.get() > 0);
            }
        };
        this.addSlot(outputSlot);


        //Player Inventory
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //Hotbar
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        ((CustomItemHandler) blockEntity.getInventory()).setContentsChangedListener(0, this::inputSlotsChanged);
        ((CustomItemHandler) blockEntity.getInventory()).setContentsChangedListener(1, this::inputSlotsChanged);

        this.addDataSlot(this.cost);
    }

    public CustomSmithingMenu(int windowId, Inventory inventory, FriendlyByteBuf byteBuf) {
        this(windowId, inventory, getBlockEntity(inventory, byteBuf));
    }

    private boolean matchesSelected(Level level) {
        if(selectedRecipe == null) return false;
        if(selectedRecipe instanceof UpgradeRecipe upgradeRecipe) {
            return upgradeRecipe.matches(getSlotsAsContainer(), level);
        }else if(selectedRecipe instanceof SmithingRecipe smithingRecipe) {
            return smithingRecipe.matches(new RecipeWrapper(blockEntity.getInventory()), level);
        }
        return false;
    }

    private void inputSlotsChanged() {
        List<Recipe<?>> currentRecipes = getRecipes();
        if(currentRecipes.isEmpty()) {
            createRepairResult();
        } else {
            setSmithing(true);
            selectedRecipe = currentRecipes.get(0);
            ItemStack stack = ItemStack.EMPTY;
            int recipeCost = 0;
            if(selectedRecipe instanceof UpgradeRecipe upgradeRecipe) {
                stack = upgradeRecipe.assemble(getSlotsAsContainer());
            } else if(selectedRecipe instanceof SmithingRecipe smithingRecipe) {
                recipeCost = smithingRecipe.getCost();
                stack = smithingRecipe.assemble(new RecipeWrapper(blockEntity.getInventory()));
            }

            int nameRepairCost = 0;
            ItemStack baseStack = baseSlot.getItem();
            if(StringUtils.isBlank(this.itemName)) {
                if(stack.hasCustomHoverName()) {
                    nameRepairCost = 1;
                    stack.resetHoverName();
                }if(baseStack.hasCustomHoverName()) {
                    nameRepairCost = 1;
                    stack.resetHoverName();
                }
            } else if (!this.itemName.equals(baseStack.getHoverName().getString())) {
                nameRepairCost = 1;
                stack.setHoverName(new TextComponent(this.itemName));
            }

            cost.set(recipeCost + nameRepairCost);
            outputSlot.set(stack);
            broadcastChanges();
        }
    }

    protected void  onTake(Player player, ItemStack stack) {
        if(!player.getAbilities().instabuild) {
            player.giveExperienceLevels(-this.cost.get());
        }

        if(selectedRecipe != null) {
            stack.onCraftedBy(player.level, player, stack.getCount());
            int shrinkAmountInput = 1;
            int shrinkAmountAddition = 1;
            if (selectedRecipe instanceof SmithingRecipe) {
                shrinkAmountInput = ((SmithingRecipe) selectedRecipe).getInput().getCount();
                shrinkAmountAddition = ((SmithingRecipe) selectedRecipe).getAddition().getCount();
            }
            baseSlot.getItem().shrink(shrinkAmountInput);
            additionSlot.getItem().shrink(shrinkAmountAddition);
            inputSlotsChanged();
        } else {
            baseSlot.set(ItemStack.EMPTY);
            if(repairItemCountCost > 0) {
                ItemStack additionStack = additionSlot.getItem();
                if(!additionStack.isEmpty() && additionStack.getCount() > repairItemCountCost) {
                    additionStack.shrink(repairItemCountCost);
                } else additionSlot.set(ItemStack.EMPTY);
            } else additionSlot.set(ItemStack.EMPTY);
        }
        this.cost.set(0);
        this.access.execute((level, pos) -> level.levelEvent(1044, pos, 0));
        broadcastChanges();
    }

    protected void createRepairResult() {
        ItemStack baseStack = baseSlot.getItem();
        this.cost.set(1);
        int costCounter = 0;
        int baseRepairCost = 0;
        int nameRepairCost = 0;
        if(baseStack.isEmpty()) {
            outputSlot.set(ItemStack.EMPTY);
            this.cost.set(0);
            broadcastChanges();
        } else {
            ItemStack outputStack = baseStack.copy();
            ItemStack additionStack = additionSlot.getItem();
            Map<Enchantment, Integer> baseEnchantments = EnchantmentHelper.getEnchantments(outputStack);
            baseRepairCost += baseStack.getBaseRepairCost();
            this.repairItemCountCost = 0;
            boolean additionEnchantedBookFlag = false;

            if(!additionStack.isEmpty()) {
                baseRepairCost += additionStack.getBaseRepairCost();
                if(!onAnvilChange(itemName, baseRepairCost, player)) return;
                additionEnchantedBookFlag = additionStack.getItem() instanceof EnchantedBookItem && !EnchantedBookItem.getEnchantments(additionStack).isEmpty();
                if(outputStack.isDamageableItem() && outputStack.getItem().isValidRepairItem(baseStack, additionStack)) {
                    int sub = Math.min(outputStack.getDamageValue(), outputStack.getMaxDamage() / 4);
                    if(sub <= 0) {
                        outputSlot.set(ItemStack.EMPTY);
                        cost.set(0);
                        return;
                    }

                    int counter;
                    for(counter = 0; sub > 0 && counter < additionStack.getCount(); ++counter) {
                        int j3 = outputStack.getDamageValue() - sub;
                        outputStack.setDamageValue(j3);
                        ++costCounter;
                        sub = Math.min(outputStack.getDamageValue(), outputStack.getMaxDamage() / 4);
                    }

                    this.repairItemCountCost = counter;
                } else {
                    if(!additionEnchantedBookFlag && (!outputStack.is(additionStack.getItem()) || !outputStack.isDamageableItem())) {
                        outputSlot.set(ItemStack.EMPTY);
                        cost.set(0);
                        return;
                    }

                    if(outputStack.isDamageableItem() && !additionEnchantedBookFlag) {
                        int baseDamageDiff = baseStack.getMaxDamage() - baseStack.getDamageValue();
                        int additionDamageDiff = additionStack.getMaxDamage() - additionStack.getDamageValue();
                        int addedDurability = additionDamageDiff + outputStack.getDamageValue() * 12 / 100;
                        int newBaseDurability = baseDamageDiff + addedDurability;
                        int newBaseDamageValue = outputStack.getMaxDamage() - newBaseDurability;
                        newBaseDamageValue = Math.max(newBaseDamageValue, 0);
                        if(newBaseDamageValue < outputStack.getDamageValue()) {
                            outputStack.setDamageValue(newBaseDamageValue);
                            costCounter += 2;
                        }
                    }

                    Map<Enchantment, Integer> additionEnchantments = EnchantmentHelper.getEnchantments(additionStack);
                    boolean canBeEnchantedFlag = false;
                    boolean cantBeEnchantedFlag = false;

                    for(Enchantment additionEnchantment : additionEnchantments.keySet()) {
                        if(additionEnchantment != null) {
                            int baseEnchantmentLvl = baseEnchantments.getOrDefault(additionEnchantment, 0);
                            int newEnchantmentLvl = additionEnchantments.get(additionEnchantment);
                            newEnchantmentLvl = baseEnchantmentLvl == newEnchantmentLvl ? newEnchantmentLvl + 1 : Math.max(baseEnchantmentLvl, newEnchantmentLvl);

                            boolean canBeEnchanted = additionEnchantment.canEnchant(baseStack);
                            if(player.getAbilities().instabuild || baseStack.getItem() instanceof EnchantedBookItem) {
                                canBeEnchanted = true;
                            }

                            for(Enchantment baseEnchantment : baseEnchantments.keySet()) {
                                if(baseEnchantment != additionEnchantment && !additionEnchantment.isCompatibleWith(baseEnchantment)) {
                                    canBeEnchanted = false;
                                    ++costCounter;
                                }
                            }

                            if(!canBeEnchanted) cantBeEnchantedFlag = true;
                            else {
                                canBeEnchantedFlag = true;
                                newEnchantmentLvl = Math.min(newEnchantmentLvl, additionEnchantment.getMaxLevel());
                                baseEnchantments.put(additionEnchantment, newEnchantmentLvl);
                                int rarityCostFactor = 0;
                                switch (additionEnchantment.getRarity()) {
                                    case COMMON -> rarityCostFactor = 1;
                                    case UNCOMMON -> rarityCostFactor = 2;
                                    case RARE -> rarityCostFactor = 4;
                                    case VERY_RARE -> rarityCostFactor = 8;
                                }

                                if(additionEnchantedBookFlag) {
                                    rarityCostFactor = Math.max(1, rarityCostFactor / 2);
                                }

                                costCounter += rarityCostFactor * newEnchantmentLvl;
                                if(baseStack.getCount() > 1) costCounter = 40;
                            }
                        }
                    }
                    if(cantBeEnchantedFlag && !canBeEnchantedFlag) {
                        outputSlot.set(ItemStack.EMPTY);
                        cost.set(0);
                        broadcastChanges();
                        return;
                    }
                }
            }

            if(StringUtils.isBlank(this.itemName)) {
                if(baseStack.hasCustomHoverName()) {
                    nameRepairCost = 1;
                    costCounter += nameRepairCost;
                    outputStack.resetHoverName();
                }
            } else if (!this.itemName.equals(baseStack.getHoverName().getString())) {
                nameRepairCost = 1;
                costCounter += nameRepairCost;
                outputStack.setHoverName(new TextComponent(this.itemName));
            }
            if(additionEnchantedBookFlag && !outputStack.isBookEnchantable(additionStack)) {
                outputStack = ItemStack.EMPTY;
            }

            cost.set(baseRepairCost + costCounter);
            if(costCounter <= 0) {
                outputStack = ItemStack.EMPTY;
            }

            if(nameRepairCost == costCounter && nameRepairCost > 0 && cost.get() >= 40) {
                cost.set(39);
            }

            if(cost.get() >= 40 && !player.getAbilities().instabuild) {
                outputStack = ItemStack.EMPTY;
            }

            if(!outputStack.isEmpty()) {
                int outBaseRepairCost = outputStack.getBaseRepairCost();
                if(!additionStack.isEmpty() && outBaseRepairCost < additionStack.getBaseRepairCost()) {
                    outBaseRepairCost = additionStack.getBaseRepairCost();
                }

                if(nameRepairCost == costCounter || nameRepairCost == 0) {
                    outBaseRepairCost = outBaseRepairCost * 2 + 1;
                }

                outputStack.setRepairCost(outBaseRepairCost);
                EnchantmentHelper.setEnchantments(baseEnchantments, outputStack);
            }

            outputSlot.set(outputStack);
            broadcastChanges();
        }
    }

    public void setItemName(String name) {
        this.itemName = name;
        if (outputSlot.hasItem()) {
            ItemStack itemstack = outputSlot.getItem();
            if (StringUtils.isBlank(name)) {
                itemstack.resetHoverName();
            } else {
                itemstack.setHoverName(new TextComponent(this.itemName));
            }
        }
        this.inputSlotsChanged();
    }

    private static SmithingTableBlockEntity getBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof SmithingTableBlockEntity) {
            return (SmithingTableBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("BlockEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.access.evaluate((level, pos) -> level.getBlockState(pos).getBlock() instanceof CustomSmithingTable && player.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D, true);
    }

    private List<Recipe<?>> getRecipes() {
        RecipeWrapper customSmithingTypeWrapper = new RecipeWrapper(blockEntity.getInventory());

        List<UpgradeRecipe> listUpgrade = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, getSlotsAsContainer(), this.level);
        List<ISmithingRecipe> listSmithing = this.level.getRecipeManager().getRecipesFor(MWRecipeSerializers.SMITHING_TYPE, customSmithingTypeWrapper, this.level);

        List<Recipe<?>> recipes = new ArrayList<>();
        recipes.addAll(listUpgrade);
        recipes.addAll(listSmithing);
        return recipes;
    }

    private Container getSlotsAsContainer() {
        return new SimpleContainer(((CustomItemHandler) blockEntity.getInventory()).toNonNullList().toArray(ItemStack[]::new));
    }

    public boolean onAnvilChange(String name, int baseCost, Player player) {
        AnvilUpdateEvent e = new AnvilUpdateEvent(baseSlot.getItem(), additionSlot.getItem(), name, baseCost, player);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        if (e.getOutput().isEmpty()) return true;

        outputSlot.set(e.getOutput());
        cost.set(e.getCost());
        repairItemCountCost = e.getMaterialCost();
        broadcastChanges();
        return false;
    }

    public int getCost() {
        return cost.get();
    }

    @NotNull
    public ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = getSlot(slotIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (slotIndex != 0 && slotIndex != 1) {
                if (slotIndex >= 3 && slotIndex < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
                broadcastChanges();
            }
            else slot.setChanged();

            if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected boolean shouldQuickMoveToAdditionalSlot(@NotNull ItemStack stack) {
        return allRecipes.stream().anyMatch((recipe) -> {
            if(recipe instanceof UpgradeRecipe) {
                return ((UpgradeRecipe) recipe).isAdditionIngredient(stack);
            }else if(recipe instanceof ISmithingRecipe) {
                return ((ISmithingRecipe) recipe).isAdditionIngredient(stack);
            }
            return false;
        });
    }

    public boolean isSmithing() {
        return smithing.get() > 0;
    }

    public void setSmithing(boolean b) {
        this.smithing.set(b ? 1 : 0);
    }

}