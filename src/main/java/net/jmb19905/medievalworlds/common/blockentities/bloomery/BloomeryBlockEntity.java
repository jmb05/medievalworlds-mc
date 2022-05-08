package net.jmb19905.medievalworlds.common.blockentities.bloomery;

import net.jmb19905.medievalworlds.common.block.BellowsBlock;
import net.jmb19905.medievalworlds.common.block.BloomeryBlock;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BloomeryBlockEntity extends BlockEntity {

    public int currentBurnTime = -1;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;
    private final CustomItemHandler inventory;

    public BloomeryBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.BLOOMERY.get(), pos, state);
        this.inventory = new CustomItemHandler(2);
    }

    public void start() {
        currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1), MWRecipeSerializers.BLOOM_TYPE);
        if(currentMaxBurnTime > 0) currentBurnTime = 0;
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(blockEntity instanceof BloomeryBlockEntity entity) {
            boolean dirty = false;

            BlockPos topPos = pos.above();
            BlockState topState = level.getBlockState(topPos);

            CustomItemHandler inventory = (CustomItemHandler) entity.getInventory();
            ItemStack fuelStack = inventory.getStackInSlot(1);
            ItemStack inputStack = inventory.getStackInSlot(0);

            if(state.getValue(BloomeryBlock.CLAY)) {
                if(entity.currentBurnTime >= 0) {
                    if(entity.currentBurnTime < entity.currentMaxBurnTime) {
                        if(!entity.fuelConsumed) {
                            inventory.setStackInSlot(1, ItemStack.EMPTY);
                            entity.fuelConsumed = true;
                        }
                        entity.currentBurnTime++;
                        if(hasActiveBellow(level, state, pos)) {
                            entity.currentBurnTime++;
                            if(state.getValue(BloomeryBlock.LIT) != BloomeryBlock.BloomeryActivationProperty.ACTIVATED) state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.ACTIVATED);
                        }else if (state.getValue(BloomeryBlock.LIT) != BloomeryBlock.BloomeryActivationProperty.LIT) state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.LIT);
                    }else {
                        entity.currentBurnTime = -1;
                        if (state.getValue(BloomeryBlock.LIT) == BloomeryBlock.BloomeryActivationProperty.LIT ||
                            state.getValue(BloomeryBlock.LIT) == BloomeryBlock.BloomeryActivationProperty.ACTIVATED)
                                state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.OFF);
                        if (state.getValue(BloomeryBlock.CLAY)) state = state.setValue(BloomeryBlock.CLAY, false);
                        if (topState.getValue(BloomeryBlock.CLAY)) topState = topState.setValue(BloomeryBlock.CLAY, false);
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        inventory.setStackInSlot(1, ItemStack.EMPTY);
                    }
                    dirty = true;
                }
            } else {
                if(entity.currentBurnTime >= 0) {
                    if (entity.currentBurnTime < entity.currentMaxBurnTime) {
                        if(!entity.fuelConsumed) {
                            inventory.setStackInSlot(1, ItemStack.EMPTY);
                            entity.fuelConsumed = true;
                        }
                        entity.currentBurnTime++;
                        if(hasActiveBellow(level, state, pos)) {
                            entity.currentBurnTime++;
                            if(state.getValue(BloomeryBlock.LIT) != BloomeryBlock.BloomeryActivationProperty.ACTIVATED) state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.ACTIVATED);
                        }else if (state.getValue(BloomeryBlock.LIT) != BloomeryBlock.BloomeryActivationProperty.LIT) state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.LIT);
                    }else {
                        entity.currentBurnTime = -1;
                        if (state.getValue(BloomeryBlock.LIT) == BloomeryBlock.BloomeryActivationProperty.LIT ||
                            state.getValue(BloomeryBlock.LIT) == BloomeryBlock.BloomeryActivationProperty.ACTIVATED)
                                state = state.setValue(BloomeryBlock.LIT, BloomeryBlock.BloomeryActivationProperty.OFF);

                        int multiplier = inputStack.getCount();

                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        inventory.setStackInSlot(1, ItemStack.EMPTY);

                        BloomRecipe recipe = Objects.requireNonNull(getRecipe(level, inputStack));
                        ItemStack primaryOutput = recipe.getPrimaryOutput().copy();
                        int primaryAddition = Util.getRandomIntInRange(-recipe.getPrimaryOffset(), recipe.getPrimaryOffset());
                        primaryOutput.grow(primaryAddition);
                        primaryOutput.setCount(primaryOutput.getCount() * multiplier);

                        ItemStack primaryPacked = recipe.getPrimaryOutputPacked().copy();
                        if(primaryPacked.getCount() != 0) {
                            int divisor = primaryPacked.getCount();
                            primaryPacked.setCount(primaryOutput.getCount() / divisor);
                            primaryOutput.setCount(primaryOutput.getCount() % divisor);
                        }

                        if(!primaryOutput.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), primaryOutput);
                        if(!primaryPacked.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), primaryPacked);

                        ItemStack secondaryOutput = recipe.getSecondaryOutput().copy();
                        int secondaryAddition = Util.getRandomIntInRange(-recipe.getSecondaryOffset(), recipe.getSecondaryOffset());
                        secondaryOutput.grow(secondaryAddition);
                        secondaryOutput.setCount(secondaryOutput.getCount() * multiplier);

                        ItemStack secondaryPacked = recipe.getSecondaryOutputPacked().copy();
                        if(secondaryPacked.getCount() != 0) {
                            int divisor = secondaryPacked.getCount();
                            secondaryPacked.setCount(secondaryOutput.getCount() / divisor);
                            secondaryOutput.setCount(secondaryOutput.getCount() % divisor);
                        }

                        if(!secondaryOutput.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), secondaryOutput);
                        if(!secondaryPacked.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), secondaryPacked);
                    }
                    dirty = true;
                }
            }

            if(dirty) {
                entity.setChanged();
                level.setBlockAndUpdate(pos, state);
                level.setBlockAndUpdate(topPos, topState);
            }
        }
    }

    private static boolean hasActiveBellow(Level level, BlockState state, BlockPos pos) {
        Direction direction = state.getValue(BloomeryBlock.FACING).getClockWise();
        BlockPos pos1 = pos.relative(direction);
        BlockPos pos2 = pos.relative(direction.getOpposite());

        if(level.getBlockState(pos1).getBlock() instanceof BellowsBlock) {
            return level.getBlockEntity(pos1) instanceof BellowsBlockEntity entity && entity.isActive();
        } else if(level.getBlockState(pos2).getBlock() instanceof BellowsBlock){
            return level.getBlockEntity(pos2) instanceof BellowsBlockEntity entity && entity.isActive();
        }
        return false;
    }

    @Nullable
    public static BloomRecipe getRecipe(Level level, ItemStack input){
        if(input == null){
            return null;
        }
        Set<Recipe<?>> recipes = Util.findRecipeByType(MWRecipeSerializers.BLOOM_TYPE, level);
        for(Recipe<?> iRecipe : recipes){
            BloomRecipe recipe = (BloomRecipe)iRecipe;
            if(recipe.getInput().test(input)) return recipe;
        }
        return null;
    }

    public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level level){
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = Util.findRecipeByType(typeIn, level);
        for(Recipe<?> recipe : recipes) if(recipe instanceof BloomRecipe) inputs.addAll(Arrays.asList(((BloomRecipe) recipe).getInput().getItems()));
        return inputs;
    }

    public final IItemHandlerModifiable getInventory(){
        return this.inventory;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    private boolean itemsAvailable(){
        return this.inventory.getStackInSlot(0).getItem() != Items.AIR && this.inventory.getStackInSlot(1).getItem() != Items.AIR;
    }

    public boolean isBurning(){
        return currentBurnTime < currentMaxBurnTime;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());

        nbt.putInt("CurrentBurnTime", currentBurnTime);
        nbt.putInt("CurrentMaxBurnTime", currentMaxBurnTime);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        NonNullList<ItemStack> inventoryAsNonNullList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsNonNullList);
        this.inventory.setNonNullList(inventoryAsNonNullList);

        this.currentBurnTime = nbt.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = nbt.getInt("CurrentMaxBurnTime");
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
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

}
