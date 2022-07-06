package net.jmb19905.medievalworlds.common.blockentities.bloomery;

import net.jmb19905.medievalworlds.common.block.BellowsBlock;
import net.jmb19905.medievalworlds.common.block.BloomeryActivationProperty;
import net.jmb19905.medievalworlds.common.block.SimpleBloomery;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.jmb19905.medievalworlds.common.menus.AdvancedBloomeryMenu;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipe;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.jmb19905.medievalworlds.util.Util;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdvancedBloomeryBlockEntity extends BlockEntity implements MenuProvider {

    public int currentBurnTime = -1;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;
    private final CustomItemHandler inventory;

    public AdvancedBloomeryBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.ADVANCED_BLOOMERY.get(), pos, state);
        this.inventory = new CustomItemHandler(10);
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof AdvancedBloomeryBlockEntity entity) {
            boolean dirty = false;

            BlockPos topPos = pos.above();
            BlockState topState = level.getBlockState(topPos);

            CustomItemHandler inventory = entity.getInventory();
            ItemStack fuelStack = inventory.getStackInSlot(3);

            if(entity.currentBurnTime >= 0) {
                if(entity.currentBurnTime < entity.currentMaxBurnTime) {
                    if(!entity.fuelConsumed) {
                        inventory.setStackInSlot(3, ItemStack.EMPTY);
                        entity.fuelConsumed = true;
                    }
                    if(hasActiveBellow(level, state, pos)) {
                        entity.currentBurnTime++;
                        if(state.getValue(SimpleBloomery.LIT) != BloomeryActivationProperty.ACTIVATED) state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.ACTIVATED);
                    }else if (state.getValue(SimpleBloomery.LIT) != BloomeryActivationProperty.LIT) state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.LIT);
                } else {
                    entity.currentBurnTime = -1;
                    if (state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.LIT ||
                            state.getValue(SimpleBloomery.LIT) == BloomeryActivationProperty.ACTIVATED)
                        state = state.setValue(SimpleBloomery.LIT, BloomeryActivationProperty.OFF);

                    handleSlot(inventory, 0, level);
                    handleSlot(inventory, 1, level);
                    handleSlot(inventory, 2, level);

                    inventory.setStackInSlot(3, ItemStack.EMPTY);
                }
                dirty = true;
            } else if(!fuelStack.isEmpty()) {
                if(hasValidInput(inventory, level)) {
                    entity.currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1), MWRecipeSerializers.BLOOM_TYPE);
                    if(entity.currentMaxBurnTime > 0) entity.currentBurnTime = 0;
                }
            }

            if(dirty) {
                entity.setChanged();
                level.setBlockAndUpdate(pos, state);
                level.setBlockAndUpdate(topPos, topState);
            }
        }
    }

    private static boolean hasValidInput(CustomItemHandler inventory, Level level) {
        List<IBloomRecipe> recipes = level.getRecipeManager().getAllRecipesFor(MWRecipeSerializers.BLOOM_TYPE);
        AtomicBoolean flag0 = new AtomicBoolean(false);
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicBoolean flag2 = new AtomicBoolean(false);
        recipes.stream().filter(recipe -> recipe.getInput().test(inventory.getStackInSlot(0))).findFirst().ifPresent(rec -> flag0.set(true));
        recipes.stream().filter(recipe -> recipe.getInput().test(inventory.getStackInSlot(1))).findFirst().ifPresent(rec -> flag1.set(true));
        recipes.stream().filter(recipe -> recipe.getInput().test(inventory.getStackInSlot(2))).findFirst().ifPresent(rec -> flag2.set(true));
        return flag0.get() || flag1.get() || flag2.get();
    }

    private static void handleSlot(CustomItemHandler inventory, int slotId, Level level) {
        ItemStack itemStack = inventory.getStackInSlot(slotId);
        int multiplier = itemStack.getCount();
        inventory.setStackInSlot(slotId, ItemStack.EMPTY);
        BloomRecipe recipe = getRecipe(level, itemStack);
        if(recipe == null) return;
        ItemStack primaryOutput = recipe.getPrimaryOutput().copy();
        int primaryAddition = Util.getRandomIntInRange(-recipe.getPrimaryOffset(), recipe.getPrimaryOffset());
        primaryOutput.grow(primaryAddition);
        primaryOutput.setCount(multiplier);

        ItemStack primaryPacked = recipe.getPrimaryOutputPacked().copy();
        if(primaryPacked.getCount() != 0) {
            int divisor = primaryPacked.getCount();
            primaryPacked.setCount(primaryOutput.getCount() / divisor);
            primaryOutput.setCount(primaryOutput.getCount() % divisor);
        }

        if(primaryOutput.getCount() > 0) {
            primaryPacked.grow(1);
        }

        inventory.setStackInSlot(slotId + 4, primaryPacked);

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

        if(secondaryOutput.getCount() > 0) {
            secondaryPacked.grow(1);
        }

        inventory.setStackInSlot(slotId + 5, secondaryPacked);
    }

    private static boolean hasActiveBellow(Level level, BlockState state, BlockPos pos) {
        Direction direction = state.getValue(SimpleBloomery.FACING).getClockWise();
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

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);

        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);

        this.currentBurnTime = nbt.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = nbt.getInt("CurrentMaxBurnTime");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());

        nbt.putInt("CurrentBurnTime", currentBurnTime);
        nbt.putInt("CurrentMaxBurnTime", currentMaxBurnTime);
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

    public CustomItemHandler getInventory() {
        return inventory;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("menu.medievalworlds.brick_bloomery");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new AdvancedBloomeryMenu(windowId, playerInventory, this);
    }
}
