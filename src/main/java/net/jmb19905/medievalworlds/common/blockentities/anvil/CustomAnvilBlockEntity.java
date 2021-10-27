package net.jmb19905.medievalworlds.common.blockentities.anvil;

import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomAnvilBlockEntity extends BlockEntity {

    private final CustomItemHandler inventory;
    private final BlockState state;

    public CustomAnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.state = state;
        this.inventory = new CustomItemHandler(1);
    }

    public CustomAnvilBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.CUSTOM_ANVIL.get(), pos, state);
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

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        super.save(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());
        return nbt;
    }

    public final IItemHandlerModifiable getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
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

    public boolean hasItems(){
        return inventory.getStackInSlot(0) != ItemStack.EMPTY;
    }
}
