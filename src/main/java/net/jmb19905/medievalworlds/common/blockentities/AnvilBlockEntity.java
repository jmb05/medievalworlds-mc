package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.LeveledAnvilBlock;
import net.jmb19905.medievalworlds.common.blockentities.abstr.MWNamedInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.menus.MWAnvilMenu;
import net.jmb19905.medievalworlds.common.recipes.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AnvilBlockEntity extends MWNamedInventoryBlockEntity {
    private final BlockState state;
    private AnvilRecipe currentRecipe;
    private boolean pressed = false;
    private int pressedTimer = 0;

    public AnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, 2, pos, state);
        this.state = state;
    }

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.CUSTOM_ANVIL.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        if(!level.isClientSide && entity instanceof AnvilBlockEntity blockEntity) {
            if (blockEntity.isPressed()) {
                blockEntity.pressedTimer++;
                if (blockEntity.pressedTimer >= 10) {
                    blockEntity.pressed = false;
                    blockEntity.pressedTimer = 0;
                }
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean hammer() {
        pressed = true;
        if(currentRecipe == null || !hasValidIngredient()) {
            currentRecipe = null;
            return false;
        }else {
            ItemStack outputSlotStack = inventory.getStackInSlot(1);
            ItemStack result = currentRecipe.getDamageScaledResult(inventory.getStackInSlot(0));
            if(outputSlotStack.isEmpty() || ItemStack.isSameItemSameTags(outputSlotStack, result)) {
                inventory.decreaseStackSize(0, currentRecipe.getInput().getCount());
                if(!outputSlotStack.isEmpty()) outputSlotStack.grow(result.getCount());
                else inventory.setStackInSlot(1, result);
                if (!hasValidIngredient()) currentRecipe = null;
                assert level != null;
                setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            return true;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasValidIngredient() {
        return currentRecipe.matches(inventory.getStackInSlot(0));
    }

    public void setCurrentRecipe(AnvilRecipe currentRecipe) {
        if (getAnvilLevel() >= currentRecipe.getMinAnvilLevel()) {
            this.currentRecipe = currentRecipe;
        }
    }

    public AnvilRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public ItemStack getItemToShow(){
        ItemStack inputItem = inventory.getStackInSlot(0);
        ItemStack resultItem = inventory.getStackInSlot(1);
        if(!resultItem.isEmpty()) return resultItem;
        else return inputItem;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + MedievalWorlds.MOD_ID + ".anvil");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, @NotNull Inventory inventory, @NotNull Player player) {
        MWAnvilMenu menu = new MWAnvilMenu(windowID, inventory, this);
        menu.setSelectedRecipeIndexByRecipe(getCurrentRecipe());
        return menu;
    }

    public int getAnvilLevel() {
        if (state.hasProperty(LeveledAnvilBlock.ANVIL_LEVEL)) {
            return state.getValue(LeveledAnvilBlock.ANVIL_LEVEL);
        }
        return 1;
    }
}