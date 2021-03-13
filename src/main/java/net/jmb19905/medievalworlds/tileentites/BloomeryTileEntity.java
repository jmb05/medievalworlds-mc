package net.jmb19905.medievalworlds.tileentites;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.containers.BloomeryContainer;
import net.jmb19905.medievalworlds.recipes.BloomRecipe;
import net.jmb19905.medievalworlds.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.registries.RecipeSerializerRegistryHandler;
import net.jmb19905.medievalworlds.registries.TileEntityTypeRegistryHandler;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BloomeryTileEntity extends TileEntity implements ITickableTileEntity {

    public BloomeryTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

    }

    public static class Bottom extends BloomeryTileEntity implements INamedContainerProvider{

        private ITextComponent customName;
        public int currentSmeltTime = 0;
        public final int maxSmeltTime = 200 - 2;
        public int currentBurnTime = 0;
        public int currentMaxBurnTime = 0;
        private int lastValidMaxBurnTime = -1;
        public boolean fuelConsumed = false;
        private final CustomItemHandler inventory;

        public Bottom() {
            super(TileEntityTypeRegistryHandler.BLOOMERY_BOTTOM.get());
            this.inventory = new CustomItemHandler(2);
        }

        @Override
        public ITextComponent getDisplayName() {
            return this.getName();
        }

        public ITextComponent getName() {
            return customName != null ? customName : this.getDefaultName();
        }

        private ITextComponent getDefaultName() {
            return new TranslationTextComponent("container." + MedievalWorlds.MOD_ID + ".bloomery");
        }

        public void setCustomName(ITextComponent name){
            this.customName = name;
        }

        @Nullable
        public ITextComponent getCustomName() {
            return customName;
        }

        @Override
        public void read(BlockState state, @Nonnull CompoundNBT compound) {
            super.read(state, compound);
            if(compound.contains("CustomName", Constants.NBT.TAG_STRING)){
                this.customName = ITextComponent.Serializer.getComponentFromJson(compound.getString("CustomName"));
            }

            NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compound, inv);
            this.inventory.setNonNullList(inv);

            this.currentSmeltTime = compound.getInt("CurrentSmeltTime");
            this.currentBurnTime = compound.getInt("CurrentBurnTime");
        }

        @Nonnull
        @Override
        public CompoundNBT write(@Nonnull CompoundNBT compound) {
            super.write(compound);
            if(this.customName != null){
                compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
            }
            ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

            compound.putInt("CurrentSmeltTime", currentSmeltTime);
            compound.putInt("CurrentBurnTime", currentBurnTime);

            return compound;
        }

        @Nullable
        @Override
        public Container createMenu(int windowID, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player) {
            return new BloomeryContainer(windowID, playerInventory, this);
        }

        @Override
        public void tick() {
            BlockPos topPos = new BlockPos(getPos().getX(), getPos().getY() + 1, getPos().getZ());
            assert world != null;
            if(world.getBlockState(topPos).getBlock() != BlockRegistryHandler.BLOOMERY_TOP_BLOCK.get()){
                world.destroyBlock(getPos(), true);
            }
        }

        @Nullable
        private BloomRecipe getRecipe(ItemStack input){
            if(input == null){
                return null;
            }
            Set<IRecipe<?>> recipes = findRecipeByType(RecipeSerializerRegistryHandler.BLOOM_TYPE, this.world);
            for(IRecipe<?> iRecipe : recipes){
                BloomRecipe recipe = (BloomRecipe)iRecipe;
                assert this.world != null;
                if(recipe.matches(new RecipeWrapper(inventory), this.world)){
                    return recipe;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        public static Set<IRecipe<?>> findRecipeByType(IRecipeType<?> typeIn, World world) {
            return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
        }

        @SuppressWarnings("unchecked")
        @OnlyIn(Dist.CLIENT)
        public static Set<IRecipe<?>> findRecipeByType(IRecipeType<?> typeIn) {
            ClientWorld world = Minecraft.getInstance().world;
            return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
        }

        public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World worldIn){
            Set<ItemStack> inputs = new HashSet<>();
            Set<IRecipe<?>> recipes = findRecipeByType(typeIn, worldIn);
            for(IRecipe<?> recipe : recipes){
                if(recipe instanceof BloomRecipe) {
                    ItemStack input = ((BloomRecipe) recipe).getInput();
                    inputs.add(input);
                }
            }
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

    }

    public static class Top extends BloomeryTileEntity{

        public Top() {
            super(TileEntityTypeRegistryHandler.BLOOMERY_TOP.get());
        }

        @Override
        public void tick() {
            BlockPos bottomPos = new BlockPos(getPos().getX(), getPos().getY() - 1, getPos().getZ());
            assert world != null;
            if(world.getBlockState(bottomPos).getBlock() != BlockRegistryHandler.BLOOMERY_BOTTOM_BLOCK.get()){
                world.destroyBlock(getPos(), false);
            }
        }

    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert world != null;
        BlockState blockState = world.getBlockState(pos);
        this.read(blockState, pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.read(state, nbt);
    }

}
