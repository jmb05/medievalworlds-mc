package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.MWItemHandler;
import net.jmb19905.medievalworlds.util.MWUtil;
import net.jmb19905.medievalworlds.util.slots.InputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomAnvilMenu extends AbstractContainerMenu {

    private final AnvilBlockEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;
    private final Level level;
    private List<AnvilRecipe> recipes = new ArrayList<>();
    private ItemStack input = ItemStack.EMPTY;
    private final Slot inputSlot;
    private final OutputSlot resultSlot;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    Runnable slotUpdateListener = () -> {};

    public CustomAnvilMenu(final int windowID, final Inventory playerInventory, final AnvilBlockEntity tile) {
        super(MWMenuTypes.ANVIL.get(), windowID);
        this.blockEntity = tile;
        this.containerLevelAccess = ContainerLevelAccess.create(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos());
        this.level = playerInventory.player.level;

        final int slotSizePlus2 = 18;
        final int startX = 8;

        // Hotbar
        int hotbarY = 142;
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
        }

        // Main Player Inventory
        final int startY = 84;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }

        //Input
        ((MWItemHandler) tile.getInventory()).setContentsChangedListener(0, () -> {
            slotsChanged();
            slotUpdateListener.run();
        });

        inputSlot = new InputSlot(tile.getInventory(), 0, 20, 33, MWRecipeSerializers.ANVIL_TYPE);
        this.addSlot(inputSlot);

        //Output
        resultSlot = new OutputSlot(tile.getInventory(), 1, 143, 32);
        this.addSlot(resultSlot);
    }

    public CustomAnvilMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    public BlockPos getBlockEntityPos() {
        return blockEntity.getBlockPos();
    }

    private static AnvilBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof AnvilBlockEntity) {
            return (AnvilBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("BlockEntity is not correct " + tileAtPos);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<AnvilRecipe> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return containerLevelAccess.evaluate((level, pos) -> level.getBlockState(pos).getBlock() instanceof AnvilBlock && player.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int index) {
        if (this.isValidRecipeIndex(index))
            this.selectedRecipeIndex.set(index);
        return true;
    }

    public void setSelectedRecipeIndexByRecipe(AnvilRecipe recipe) {
        if(recipe == null) {
            this.selectedRecipeIndex.set(-1);
            return;
        }
        slotsChanged();
        int index = this.recipes.indexOf(recipe);
        if(isValidRecipeIndex(index)) this.selectedRecipeIndex.set(index);
        broadcastChanges();
    }

    private boolean isValidRecipeIndex(int index) {
        return index >= 0 && index < this.recipes.size();
    }

    public void slotsChanged() {
        ItemStack itemstack = this.inputSlot.getItem();
        if (!itemstack.is(this.input.getItem())) {
            this.input = itemstack.copy();
            this.setupRecipeList(itemstack);
        }
    }

    private void setupRecipeList(ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        if (!stack.isEmpty()) {
            this.recipes = MWUtil.findRecipeByType(MWRecipeSerializers.ANVIL_TYPE, this.level)
                    .stream()
                    .filter(recipe -> ((AnvilRecipe) recipe).matches(this.input))
                    .filter(recipe -> ((AnvilRecipe) recipe).getMinAnvilLevel() <= blockEntity.getAnvilLevel())
                    .map(recipe -> (AnvilRecipe) recipe)
                    .collect(Collectors.toList());
        }
        this.broadcastChanges();
    }

    void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            AnvilRecipe anvilRecipe = this.recipes.get(this.selectedRecipeIndex.get());
            this.resultSlot.set(anvilRecipe.getResultItem().copy());
        } else this.resultSlot.set(ItemStack.EMPTY);
        this.broadcastChanges();
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 1) {
                item.onCraftedBy(itemstack1, player.level, player);
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, new SimpleContainer(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }

}
