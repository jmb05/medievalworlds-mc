package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.FletchingTableBlockEntity;
import net.jmb19905.medievalworlds.common.recipes.fletching.FletchingRecipe;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.core.registries.VanillaReplacements;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.jmb19905.medievalworlds.util.slots.InputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class FletchingMenu extends AbstractContainerMenu {

    private final Level level;
    private final ContainerLevelAccess access;
    private final FletchingTableBlockEntity blockEntity;

    private FletchingRecipe currentRecipe;

    public FletchingMenu(int windowId, Inventory playerInv, FletchingTableBlockEntity blockEntity) {
        super(MWMenuTypes.FLETCHING_MENU.get(), windowId);
        this.level = playerInv.player.level;
        this.access = ContainerLevelAccess.create(level, blockEntity.getBlockPos());
        this.blockEntity = blockEntity;

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
            this.addSlot(new OutputSlot(itemHandler, 0, 116, 33){
                @Override
                public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                    FletchingMenu.this.onTake(player, stack);
                }
            });
            this.addSlot(new InputSlot(itemHandler, 1, 44, 14));
            this.addSlot(new InputSlot(itemHandler, 2, 44, 33));
            this.addSlot(new InputSlot(itemHandler, 3, 44, 52));
        });

        //Player Inventory
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //Hotbar
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }


        ((CustomItemHandler) blockEntity.getInventory()).setContentsChangedListener(1, this::inputSlotsChanged);
        ((CustomItemHandler) blockEntity.getInventory()).setContentsChangedListener(2, this::inputSlotsChanged);
        ((CustomItemHandler) blockEntity.getInventory()).setContentsChangedListener(3, this::inputSlotsChanged);
    }

    public FletchingMenu(int windowId, Inventory playerInv, FriendlyByteBuf buffer) {
        this(windowId, playerInv, (FletchingTableBlockEntity) Objects.requireNonNull(playerInv.player.level.getBlockEntity(buffer.readBlockPos())));
    }

    private void inputSlotsChanged() {
        RecipeWrapper wrapper = new RecipeWrapper(blockEntity.getInventory());
        List<IFletchingRecipe> recipes = getRecipes(wrapper);
        if(recipes.size() > 0) {
            for(IFletchingRecipe iFletchingRecipe : recipes) {
                System.out.println(iFletchingRecipe.matches(wrapper, level));
            }
            currentRecipe = (FletchingRecipe) recipes.get(0);
            if(!slots.get(0).hasItem() || slots.get(0).getItem().is(currentRecipe.getResultItem().getItem())) {
                slots.get(0).set(currentRecipe.assemble(wrapper));
            }
        }
    }

    public void onTake(Player player, ItemStack stack) {
        if(currentRecipe != null) {
            stack.onCraftedBy(player.level, player, stack.getCount());
            slots.get(1).getItem().shrink(1);
            slots.get(2).getItem().shrink(1);
            slots.get(3).getItem().shrink(1);
            inputSlotsChanged();
        }
        this.access.execute((level, pos) -> level.levelEvent(1044, pos, 0));
        broadcastChanges();
    }

    private List<IFletchingRecipe> getRecipes(RecipeWrapper wrapper) {
        return this.level.getRecipeManager().getRecipesFor(MWRecipeSerializers.FLETCHING_TYPE, wrapper, this.level);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(access, player, VanillaReplacements.FLETCHING_TABLE.get());
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
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!

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
