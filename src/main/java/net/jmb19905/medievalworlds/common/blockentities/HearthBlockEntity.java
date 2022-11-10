package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.hearth.HearthBlock;
import net.jmb19905.medievalworlds.common.block.hearth.HearthLitProperty;
import net.jmb19905.medievalworlds.common.block.hearth.HearthPartProperty;
import net.jmb19905.medievalworlds.common.blockentities.abstr.MWInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.jmb19905.medievalworlds.common.item.HeatedItem;
import net.jmb19905.medievalworlds.common.menus.HearthMenu;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class HearthBlockEntity extends MWInventoryBlockEntity {

    public int currentHeatTime = 0;
    public final int maxHeatTime = 200 - 2;
    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;
    private boolean hasAlreadyTicked = false;

    public HearthBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.HEARTH.get(), pos, state);
    }

    public HearthBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, state.getValue(HearthBlock.PART) == HearthPartProperty.MAIN ? 3 : 0, pos, state);
    }

    public static <T extends BlockEntity> void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull T blockEntity) {
        if (!level.isClientSide) {//Only do these calculations on the server
            if (blockEntity instanceof HearthBlockEntity hearth) {
                hearth.hasAlreadyTicked = true;
                boolean dirty = false;
                MWItemHandler inventory = hearth.inventory;
                Optional<HeatedItem> currentHeatedItem = findHeatItem(inventory.getStackInSlot(0).getItem());
                if (currentHeatedItem.isPresent()) {
                    if (hearth.currentBurnTime < hearth.currentMaxBurnTime && hearth.currentBurnTime >= 0 && inventory.getStackInSlot(2).getCount() < 64) {
                        if (!hearth.fuelConsumed) {
                            inventory.decreaseStackSize(1, 1);
                            hearth.fuelConsumed = true;
                        }
                        if (hearth.currentHeatTime < hearth.maxHeatTime) {
                            if (level.getBlockState(pos).getValue(HearthBlock.LIT) != HearthLitProperty.ON) {
                                level.setBlockAndUpdate(pos, state.setValue(HearthBlock.LIT, HearthLitProperty.ON));
                            }
                            hearth.currentHeatTime++;
                        } else {
                            hearth.currentHeatTime = 0;
                            inventory.insertItem(2, new ItemStack(currentHeatedItem.get()), false);
                            inventory.decreaseStackSize(0, 1);
                        }
                        dirty = true;
                    } else if (ForgeHooks.getBurnTime(inventory.getStackInSlot(1), RecipeType.BLASTING) > 0 && inventory.getStackInSlot(2).getCount() < 64) {
                        hearth.currentBurnTime = 0;
                        hearth.fuelConsumed = false;
                        hearth.currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1), RecipeType.BLASTING);
                        dirty = true;
                    } else {
                        hearth.currentBurnTime = -1;
                        hearth.fuelConsumed = false;
                        hearth.currentMaxBurnTime = 0;
                        level.setBlockAndUpdate(pos, state.setValue(HearthBlock.LIT, HearthLitProperty.OFF));
                        if (hearth.currentHeatTime > 0) {
                            hearth.currentHeatTime -= 2;
                        }
                        dirty = true;
                    }
                } else {
                    //If there are no valid Ingredients we turn of the fire but only if there is no fuel still burning and...
                    if (level.getBlockState(pos).getValue(HearthBlock.LIT) == HearthLitProperty.ON && hearth.currentBurnTime >= hearth.currentMaxBurnTime) {
                        level.setBlockAndUpdate(pos, state.setValue(HearthBlock.LIT, HearthLitProperty.OFF));
                    }
                    //...we decrease the heat timer
                    if (hearth.currentHeatTime < hearth.maxHeatTime && hearth.currentHeatTime > 0) {
                        hearth.currentHeatTime -= 2;
                    }
                }

                //The increase the fuel timer if fuel is (still) burning
                //But only if the timer is bigger than 0
                if (hearth.currentBurnTime < hearth.currentMaxBurnTime && hearth.currentBurnTime >= 0) {
                    hearth.currentBurnTime++;
                    dirty = true;
                }
                //The heat timer should not be smaller than 0
                if (hearth.currentHeatTime < 0) {
                    hearth.currentHeatTime = 0;
                    dirty = true;
                }
                //if the block has changed we write the changes to the disk,
                //notify the neighbours and send the update to the client
                if(dirty){
                    hearth.setChanged();
                    level.sendBlockUpdated(pos, state, state, 3);
                }
            }
        }
    }

    private static Optional<HeatedItem> findHeatItem(Item item) {
        return ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(entry -> entry.getValue() instanceof HeatedItem)
                .map(entry -> ((HeatedItem) entry.getValue()))
                .filter(heatedItem -> heatedItem.getBaseItem() == item)
                .findFirst();
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.currentHeatTime = tag.getInt("CurrentHeatTime");
        this.currentBurnTime = tag.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = tag.getInt("CurrentMaxBurnTime");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CurrentHeatTime", currentHeatTime);
        tag.putInt("CurrentBurnTime", currentBurnTime);
        tag.putInt("CurrentMaxBurnTime", currentMaxBurnTime);
    }

    public float getCoalFillLevel() {
        ItemStack stack = inventory.getStackInSlot(1);
        return (float) stack.getCount() / (float) stack.getMaxStackSize();
    }

    public int getCoalFillLevelInt() {
        int coalLevel = (int) (getCoalFillLevel() * 3) + 1;
        if (getCoalFillLevel() <= 0) coalLevel = 0;
        return coalLevel;
    }

    public boolean isReadyForUse() {
        return hasAlreadyTicked;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inv, @NotNull Player player) {
        return new HearthMenu(windowId, inv, this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container." + MedievalWorlds.MOD_ID + ".hearth");
    }
}
