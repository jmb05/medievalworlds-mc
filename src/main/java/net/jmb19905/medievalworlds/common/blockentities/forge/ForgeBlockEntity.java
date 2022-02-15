package net.jmb19905.medievalworlds.common.blockentities.forge;

import net.jmb19905.medievalworlds.common.multiblock.ForgeShape;
import net.jmb19905.medievalworlds.common.recipes.forge.ForgeRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ForgeBlockEntity extends BlockEntity {

    private final CustomItemHandler inventory;

    public int currentBurnTimer = 0;
    public int[] currentHeatTime = new int[8];
    public int[] currentMaxHeatTime = new int[8];
    private ForgeShape shape;

    public ForgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = new CustomItemHandler(9);
    }

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.FORGE.get(), pos, state);
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof ForgeBlockEntity entity) {
            boolean dirty = false;

            ForgeShape.findForgeShape(level, pos).ifPresentOrElse(forgeShape -> entity.shape = forgeShape, () -> entity.disassemble(level, pos));

            if(!entity.shape.isFormed()) entity.disassemble(level, pos);

            if(entity.currentBurnTimer > 0) entity.currentBurnTimer--;

            CustomItemHandler inventory = entity.inventory;

            for(int i=0;i<inventory.getSlots() - 1;i++) {
                if(entity.currentMaxHeatTime[i] > 0) {
                    if(entity.currentHeatTime[i] < entity.currentMaxHeatTime[i]) {

                    }
                }
            }

            if(dirty) entity.markUpdated();
        }
    }

    private void disassemble(Level level, BlockPos pos) {
        level.destroyBlock(pos, false);
        level.setBlockAndUpdate(pos, Blocks.BRICKS.defaultBlockState());
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T blockEntity) {

    }

    public void replenishFuel(ItemStack stack) {
        stack.setCount(0);
        currentBurnTimer += (ForgeHooks.getBurnTime(stack, MWRecipeSerializers.FORGE_TYPE) * stack.getCount());
    }

    public boolean placeItem(ItemStack stack){
        if(!isHeatable(stack)) return false;
        for(int i=0;i<inventory.getSlots() - 1;i++) {
            if(inventory.getStackInSlot(i).isEmpty()) {
                ForgeRecipe recipe = getRecipe(stack);
                assert recipe != null;
                currentMaxHeatTime[i] = recipe.getHeatTime();
                currentHeatTime[i] = 0;
                inventory.setStackInSlot(i, stack.split(1));
                markUpdated();
                return true;
            }
        }
        return false;
    }

    private void markUpdated() {
        this.setChanged();
        Objects.requireNonNull(this.getLevel()).sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    private boolean isHeatable(ItemStack stack) {
        return getRecipe(stack) != null;
    }

    @Nullable
    private ForgeRecipe getRecipe(ItemStack input){
        if(input == null){
            return null;
        }
        Set<Recipe<?>> recipes = findRecipeByType(MWRecipeSerializers.ALLOY_TYPE, this.level);
        for(Recipe<?> iRecipe : recipes){
            ForgeRecipe recipe = (ForgeRecipe)iRecipe;
            assert this.level != null;
            if(recipe.matches(new RecipeWrapper(inventory), this.level)){
                return recipe;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn, Level world) {
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn) {
        ClientLevel world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }

    public CustomItemHandler getInventory() {
        return inventory;
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);

        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);

        this.currentBurnTimer = nbt.getInt("CurrentBurnTimer");
        this.currentHeatTime = nbt.getIntArray("CurrentHeatTime");
        this.currentMaxHeatTime = nbt.getIntArray("CurrentMaxHeatTime");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());

        nbt.putInt("CurrentBurnTimer", currentBurnTimer);
        nbt.putIntArray("CurrentHeatTime", currentHeatTime);
        nbt.putIntArray("CurrentMaxHeatTime", currentMaxHeatTime);
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
}
