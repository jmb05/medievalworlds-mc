package net.jmb19905.medievalworlds.common.blockentities.abstr;

import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class MWInventoryBlockEntity extends BlockEntity implements IMWInventoryHandler, MenuProvider {

    protected final MWItemHandler inventory;
    private final LazyOptional<IItemHandler> holder;

    public MWInventoryBlockEntity(BlockEntityType<?> type, int invSize, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = new MWItemHandler(invSize);
        holder = LazyOptional.of(() -> inventory);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());
    }

    @Override
    public final MWItemHandler getInventory() {
        return inventory;
    }
    public Predicate<Integer> isDropSlot() {
        return i -> true;
    }

    @javax.annotation.Nullable
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

    @SuppressWarnings("removal")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap);
    }
}
