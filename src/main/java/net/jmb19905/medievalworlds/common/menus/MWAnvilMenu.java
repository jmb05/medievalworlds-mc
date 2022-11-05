package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.block.CustomAnvilBlock;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.inv.slots.InputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.OutputSlot;
import net.jmb19905.medievalworlds.common.recipes.MWRecipeHelper;
import net.jmb19905.medievalworlds.common.recipes.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MWAnvilMenu extends MWInventoryMenu<AnvilBlockEntity> {
    private List<AnvilRecipe> recipes = new ArrayList<>();
    private ItemStack input = ItemStack.EMPTY;
    private  Slot inputSlot;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    Runnable slotUpdateListener = () -> {};

    public MWAnvilMenu(final int windowID, final Inventory playerInventory, final AnvilBlockEntity blockEntity) {
        super(MWMenuTypes.ANVIL.get(), windowID, playerInventory, blockEntity);
        addDataSlot(selectedRecipeIndex);
    }

    public MWAnvilMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getAnvilBlockEntity(playerInventory, data));
    }

    @Override
    protected int addInputSlots() {
        inputSlot = new InputSlot(blockEntity.getInventory(), 0, 20, 33);
        this.addSlot(inputSlot);
        return 1;
    }

    @Override
    protected int addOutputSlots() {
        OutputSlot resultSlot = new OutputSlot(blockEntity.getInventory(), 1, 143, 32);
        this.addSlot(resultSlot);
        return 1;
    }

    public BlockPos getBlockEntityPos() {
        return blockEntity.getBlockPos();
    }

    private static AnvilBlockEntity getAnvilBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        final BlockEntity blockEntityAtPos = getBlockEntity(playerInv, data);
        if (blockEntityAtPos instanceof AnvilBlockEntity anvilBlockEntity) {
            return anvilBlockEntity;
        }
        throw new IllegalStateException("BlockEntity is not correct " + blockEntityAtPos);
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
    public Predicate<BlockState> isValidBlockState() {
        return state -> state.getBlock() instanceof CustomAnvilBlock;
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
        slotsChanged(0);
        int index = this.recipes.indexOf(recipe);
        if(isValidRecipeIndex(index)) this.selectedRecipeIndex.set(index);
        broadcastChanges();
    }

    private boolean isValidRecipeIndex(int index) {
        return index >= 0 && index < this.recipes.size();
    }

    @Override
    protected void slotsChanged(int index) {
        if (index == 0) {
            ItemStack itemstack = this.inputSlot.getItem();
            if (!itemstack.is(this.input.getItem())) {
                this.input = itemstack.copy();
                this.setupRecipeList(itemstack);
            }
            slotUpdateListener.run();
        }
    }

    private void setupRecipeList(ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        if (!stack.isEmpty()) {
            this.recipes = MWRecipeHelper.findRecipeByType(MWRecipeSerializers.ANVIL_TYPE, this.level)
                    .stream()
                    .filter(recipe -> ((AnvilRecipe) recipe).matches(this.input))
                    .filter(recipe -> ((AnvilRecipe) recipe).getMinAnvilLevel() <= blockEntity.getAnvilLevel())
                    .map(recipe -> (AnvilRecipe) recipe)
                    .collect(Collectors.toList());
        }
        this.broadcastChanges();
    }

    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }

}
