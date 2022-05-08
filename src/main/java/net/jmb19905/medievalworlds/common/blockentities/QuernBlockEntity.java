package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QuernBlockEntity extends BlockEntity {

    private boolean turning = false;
    private int turningTimer = 0;

    public QuernBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public QuernBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.QUERN.get(), pos, state);
    }

    public void turn() {
        turningTimer = 10;
    }

    private void setTurning(boolean turning, Level level, BlockPos pos, BlockState state) {
        this.turning = turning;
        level.sendBlockUpdated(pos, state, state, 2);
    }

    public boolean isTurning() {
        return turning;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, QuernBlockEntity blockEntity) {
        if(blockEntity.turningTimer > 0) {
            blockEntity.turningTimer++;
            if(!blockEntity.turning) blockEntity.setTurning(true, level, pos, state);
        } else {
            if(blockEntity.turning) blockEntity.setTurning(false, level, pos, state);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("turning", turning);
        return super.getUpdateTag();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        turning = tag.getBoolean("turning");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
