package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.bloomery.AdvancedBloomeryBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
import net.jmb19905.medievalworlds.util.slots.InputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AdvancedBloomeryMenu extends AbstractContainerMenu {

    private final AdvancedBloomeryBlockEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;

    public AdvancedBloomeryMenu(int windowId, final Inventory playerInventory, final AdvancedBloomeryBlockEntity blockEntity) {
        super(MWMenuTypes.ADVANCED_BLOOMERY_MENU.get(), windowId);
        this.blockEntity = blockEntity;
        this.containerLevelAccess = ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos());

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
        this.addSlot(new InputSlot(blockEntity.getInventory(), 0, 15, 17, MWRecipeSerializers.BLOOM_TYPE));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 1, 34, 17, MWRecipeSerializers.BLOOM_TYPE));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 2, 53, 17, MWRecipeSerializers.BLOOM_TYPE));
        this.addSlot(new FuelInputSlot(blockEntity.getInventory(), 3, 34, 51, MWRecipeSerializers.BLOOM_TYPE));

        //Output
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 4, 108, 23));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 5, 108, 45));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 6, 127, 23));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 7, 127, 45));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 8, 146, 23));
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 9, 146, 45));
    }

    public AdvancedBloomeryMenu(int windowId, final  Inventory playerInventory, FriendlyByteBuf buffer) {
        this(windowId, playerInventory, getBlockEntity(playerInventory, buffer));
    }

    private static AdvancedBloomeryBlockEntity getBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof AdvancedBloomeryBlockEntity) {
            return (AdvancedBloomeryBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled(){
        return (this.blockEntity.currentBurnTime == 0 || this.blockEntity.currentMaxBurnTime == 0) ? 0 : (this.blockEntity.currentBurnTime * 24 / this.blockEntity.currentMaxBurnTime);
    }


    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return (this.blockEntity.currentBurnTime == 0 || this.blockEntity.currentMaxBurnTime == 0) ? 0 : (this.blockEntity.currentBurnTime * 13 / this.blockEntity.currentMaxBurnTime);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.blockEntity.currentMaxBurnTime;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(containerLevelAccess, player, MWBlocks.BRICK_BLOOMERY.get());
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}
