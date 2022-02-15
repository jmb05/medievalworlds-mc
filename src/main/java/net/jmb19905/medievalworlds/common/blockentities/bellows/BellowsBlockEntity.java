package net.jmb19905.medievalworlds.common.blockentities.bellows;

import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

public class BellowsBlockEntity extends BlockEntity {

    private boolean active = false;
    private int activeCooldown = 100;

    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.BELLOWS.get(), pos, state);
    }

    public void activate(Level level) {
        this.active = true;
        this.activeCooldown = 100;
        if (!level.isClientSide) NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(level::dimension), new BellowsActivationPacket(getBlockPos(), true));
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(blockEntity instanceof BellowsBlockEntity entity) {
            if(entity.isActive()) {
                entity.activeCooldown--;
                if(entity.activeCooldown <= 0) {
                    entity.active = false;
                    NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(level::dimension), new BellowsActivationPacket(pos, false));
                }
                entity.setChanged();
            }
        }
    }

}
