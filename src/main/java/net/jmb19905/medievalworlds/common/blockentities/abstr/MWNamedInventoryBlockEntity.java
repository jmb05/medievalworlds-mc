package net.jmb19905.medievalworlds.common.blockentities.abstr;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MWNamedInventoryBlockEntity extends MWInventoryBlockEntity implements MenuProvider {

    private Component customName;

    public MWNamedInventoryBlockEntity(BlockEntityType<?> type, int invSize, BlockPos pos, BlockState state) {
        super(type, invSize, pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if(tag.contains("CustomName", Tag.TAG_STRING)) {
            this.customName = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        if(this.customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
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

    protected abstract Component getDefaultName();

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.getName();
    }
}