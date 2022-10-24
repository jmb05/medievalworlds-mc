package net.jmb19905.medievalworlds.common.blockentities.bloomery;

import net.jmb19905.medievalworlds.common.block.BloomeryActivationProperty;
import net.jmb19905.medievalworlds.common.block.SimpleBloomery;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipe;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.MWItemHandler;
import net.jmb19905.medievalworlds.util.MWUtil;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SimpleBloomeryBlockEntity extends BlockEntity {

    public int currentBurnTime = -1;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;
    private final MWItemHandler inventory;

    public SimpleBloomeryBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.SIMPLE_BLOOMERY.get(), pos, state);
        this.inventory = new MWItemHandler(2);
    }

    public void start() {
        currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1), MWRecipeSerializers.BLOOM_TYPE);
        if(currentMaxBurnTime > 0) currentBurnTime = 0;
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(blockEntity instanceof SimpleBloomeryBlockEntity entity) {
            boolean dirty = false;

            BlockPos topPos = pos.above();
            BlockState topState = level.getBlockState(topPos);

            MWItemHandler inventory = (MWItemHandler) entity.getInventory();
            ItemStack fuelStack = inventory.getStackInSlot(1);
            ItemStack inputStack = inventory.getStackInSlot(0);

            if(state.getValue(SimpleBloomery.CLAY)) {
                if(entity.currentBurnTime >= 0) {
                    if(entity.currentBurnTime < entity.currentMaxBurnTime) {
                        if(!entity.fuelConsumed) {
                            inventory.setStackInSlot(1, ItemStack.EMPTY);
                            entity.fuelConsumed = true;
                        }
                        entity.currentBurnTime++;
                        state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.LIT);
                    }else {
                        entity.currentBurnTime = -1;
                        if (state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.LIT ||
                                state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.ACTIVATED)
                            state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.OFF);
                        if (state.getValue(SimpleBloomery.CLAY)) state = state.setValue(SimpleBloomery.CLAY, false);
                        if (topState.getValue(SimpleBloomery.CLAY)) topState = topState.setValue(SimpleBloomery.CLAY, false);
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
                        state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.LIT);
                    } else {
                        entity.currentBurnTime = -1;
                        if (state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.LIT ||
                                state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.ACTIVATED)
                            state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.OFF);

                        int multiplier = inputStack.getCount();

                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        inventory.setStackInSlot(1, ItemStack.EMPTY);

                        BloomRecipe recipe = Objects.requireNonNull(getRecipe(level, inputStack));
                        ItemStack primaryOutput = recipe.getPrimaryOutput().copy();
                        ItemStack secondaryOutput = recipe.getSecondaryOutput().copy();

                        primaryOutput.setCount(primaryOutput.getCount() * multiplier);
                        secondaryOutput.setCount(secondaryOutput.getCount() * multiplier);
                        for (int i=0;i<multiplier;i++) {
                            int primaryAddition = level.random.nextIntBetweenInclusive(-recipe.getPrimaryOffset(), recipe.getPrimaryOffset());
                            primaryOutput.grow(primaryAddition);
                            int secondaryAddition = level.random.nextIntBetweenInclusive(-recipe.getSecondaryOffset(), recipe.getSecondaryOffset());
                            secondaryOutput.grow(secondaryAddition);
                        }

                        ItemStack primaryPacked = recipe.getPrimaryOutputPacked().copy();
                        createDrops(level, pos, primaryOutput, primaryPacked);
                        ItemStack secondaryPacked = recipe.getSecondaryOutputPacked().copy();
                        createDrops(level, pos, secondaryOutput, secondaryPacked);
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

    private static void createDrops(Level level, BlockPos pos, ItemStack output, ItemStack outputPacked) {
        if(outputPacked.getCount() != 0) {
            int divisorPrimary = outputPacked.getCount();
            outputPacked.setCount(output.getCount() / divisorPrimary);
            output.setCount(output.getCount() % divisorPrimary);
        }

        if(!output.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), output);
        if(!outputPacked.isEmpty()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), outputPacked);
    }

    @Nullable
    public static BloomRecipe getRecipe(Level level, ItemStack input){
        if(input == null){
            return null;
        }
        Set<Recipe<?>> recipes = MWUtil.findRecipeByType(MWRecipeSerializers.BLOOM_TYPE, level);
        for(Recipe<?> iRecipe : recipes){
            BloomRecipe recipe = (BloomRecipe)iRecipe;
            if(recipe.getInput().test(input)) return recipe;
        }
        return null;
    }

    public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level level){
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = MWUtil.findRecipeByType(typeIn, level);
        for(Recipe<?> recipe : recipes) if(recipe instanceof BloomRecipe) inputs.addAll(Arrays.asList(((BloomRecipe) recipe).getInput().getItems()));
        return inputs;
    }

    public final IItemHandlerModifiable getInventory(){
        return this.inventory;
    }

    @SuppressWarnings("removal")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
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