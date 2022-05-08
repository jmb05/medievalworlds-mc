package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class AnvilBlockEntity extends BlockEntity implements MenuProvider {

    private final CustomItemHandler inventory;
    private final BlockState state;

    private Component customName;
    private AnvilRecipe currentRecipe;

    public AnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.state = state;
        this.inventory = new CustomItemHandler(2);
    }

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.CUSTOM_ANVIL.get(), pos, state);
    }

    public void hammer() {
        if(currentRecipe == null || !currentRecipe.matches(inventory.getStackInSlot(0))) {
            currentRecipe = null;
        }else {
            ItemStack outputSlotStack = inventory.getStackInSlot(1);
            if(outputSlotStack.isEmpty() || outputSlotStack.getItem() == currentRecipe.getResultItem().getItem()) {
                inventory.decreaseStackSize(0, currentRecipe.getInput().getCount());
                if(outputSlotStack.getItem() == currentRecipe.getResultItem().getItem()) outputSlotStack.grow(currentRecipe.getResultItem().getCount());
                else inventory.setStackInSlot(1, currentRecipe.getResultItem().copy());
                assert level != null;
                setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    public void setCurrentRecipe(AnvilRecipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public AnvilRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public Direction getFacingDirection() {
        return state.getValue(AnvilBlock.FACING);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);

        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());
    }

    public final IItemHandlerModifiable getInventory() {
        return inventory;
    }

    public ItemStack getItemToShow(){
        ItemStack inputItem = inventory.getStackInSlot(0);
        ItemStack resultItem = inventory.getStackInSlot(1);
        if(!resultItem.isEmpty()) return resultItem;
        else return inputItem;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.saveAdditional(nbt);
        return ClientboundBlockEntityDataPacket.create(this, blockEntity -> nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(Objects.requireNonNull(pkt.getTag()));
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.saveAdditional(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        this.load(nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    public boolean isInputEmpty(){
        return inventory.getStackInSlot(0).isEmpty();
    }

    public void setCustomName(Component name){
        this.customName = name;
    }

    @Nullable
    public Component getCustomName() {
        return customName;
    }

    public Component getName() {
        return customName != null ? customName : this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container." + MedievalWorlds.MOD_ID + ".anvil");
    }

    @Override
    @NotNull
    public Component getDisplayName() {
        return this.getName();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, @NotNull Inventory inventory, @NotNull Player player) {
        return new CustomAnvilMenu(windowID, inventory, this);
    }
}
