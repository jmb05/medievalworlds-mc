package net.jmb19905.medievalworlds.tileentites;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.block.AlloyFurnaceBlock;
import net.jmb19905.medievalworlds.containers.AlloyFurnaceContainer;
import net.jmb19905.medievalworlds.recipes.AlloyRecipe;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AlloyFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private ITextComponent customName;
    public int currentAlloyTime = 0;
    //TODO: check why alloy time and burn time do not align
    public final int maxAlloyTime = 200 - 2;
    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    private int lastValidMaxBurnTime = -1;
    public boolean fuelConsumed = false;
    private final CustomItemHandler inventory;

    public AlloyFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new CustomItemHandler(4);
    }

    public AlloyFurnaceTileEntity(){
        this(TileEntityTypeRegistryHandler.ALLOY_FURNACE.get());
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Override
    public Container createMenu(int windowID, @Nonnull PlayerInventory playerInv, @Nonnull PlayerEntity player) {
        return new AlloyFurnaceContainer(windowID, playerInv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if(world != null && !world.isRemote) {
            boolean flipped = false;
            ItemStack inv0 = this.inventory.getStackInSlot(0);
            ItemStack inv1 = this.inventory.getStackInSlot(1);
            AlloyRecipe recipe = this.getRecipe(inv0, inv1, false);
            if(recipe == null){
                AlloyRecipe flippedRecipe = this.getRecipe(inv1, inv0, true);
                if(flippedRecipe != null){
                    recipe = flippedRecipe;
                    flipped = true;
                }
            }
            if (recipe != null) {
                if (currentBurnTime < currentMaxBurnTime && currentBurnTime >= 0 && inventory.getStackInSlot(3).getCount() < 64){
                    if (!fuelConsumed) {
                        this.inventory.decrStackSize(2, 1);
                        fuelConsumed = true;
                    }
                    if (currentAlloyTime < maxAlloyTime) {
                        if (!world.getBlockState(this.getPos()).get(AlloyFurnaceBlock.LIT)) {
                            this.world.setBlockState(this.getPos(), this.getBlockState().with(AlloyFurnaceBlock.LIT, true));
                        }
                        currentAlloyTime++;
                    } else {
                        currentAlloyTime = 0;
                        ItemStack output = Objects.requireNonNull(recipe).getRecipeOutput();
                        this.inventory.insertItem(3, output.copy(), false);
                        if(flipped) {
                            this.inventory.decrStackSize(1, recipe.getInput1().getCount());
                            this.inventory.decrStackSize(0, recipe.getInput2().getCount());
                        }else{
                            this.inventory.decrStackSize(0, recipe.getInput1().getCount());
                            this.inventory.decrStackSize(1, recipe.getInput2().getCount());
                        }
                    }
                    dirty = true;
                } else if (ForgeHooks.getBurnTime(this.inventory.getStackInSlot(2)) > 0 && inventory.getStackInSlot(3).getCount() < 64) {
                    currentBurnTime = 0;
                    fuelConsumed = false;
                    currentMaxBurnTime = ForgeHooks.getBurnTime(this.inventory.getStackInSlot(2));
                    dirty = true;
                } else{
                    currentBurnTime = -1;
                    fuelConsumed = false;
                    this.world.setBlockState(this.getPos(), this.getBlockState().with(AlloyFurnaceBlock.LIT, false));
                    if(currentAlloyTime > 0) {
                        currentAlloyTime -= 2;
                    }
                    dirty = true;
                }
            }else {
                if(this.world.getBlockState(this.getPos()).get(AlloyFurnaceBlock.LIT) && currentBurnTime >= currentMaxBurnTime) {
                    this.world.setBlockState(this.getPos(), this.getBlockState().with(AlloyFurnaceBlock.LIT, false));
                }
                if(currentAlloyTime < maxAlloyTime && currentAlloyTime > 0){
                    currentAlloyTime-=2;
                }
            }
            if(currentBurnTime < currentMaxBurnTime && currentBurnTime >= 0){
                currentBurnTime++;
                dirty = true;
            }
            if(currentAlloyTime < 0){
                currentAlloyTime = 0;
                dirty = true;
            }
        }


        if(dirty){
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    public void setCustomName(ITextComponent name){
        this.customName = name;
    }

    @Nullable
    public ITextComponent getCustomName() {
        return customName;
    }

    public ITextComponent getName() {
        return customName != null ? customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + MedievalWorlds.MOD_ID + ".alloy_furnace");
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

        this.currentAlloyTime = compound.getInt("CurrentAlloyTime");
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

        compound.putInt("CurrentAlloyTime", currentAlloyTime);
        compound.putInt("CurrentBurnTime", currentBurnTime);

        return compound;
    }

    @Nullable
    private AlloyRecipe getRecipe(ItemStack input1, ItemStack input2, boolean flipped){
        if(input1 == null || input2 == null){
            return null;
        }
        Set<IRecipe<?>> recipes = findRecipeByType(RecipeSerializerRegistryHandler.ALLOY_TYPE, this.world);
        for(IRecipe<?> iRecipe : recipes){
            AlloyRecipe recipe = (AlloyRecipe)iRecipe;
            assert this.world != null;
            if(recipe.matchesUniversally(new RecipeWrapper(inventory), this.world, flipped)){
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
            if(recipe instanceof AlloyRecipe) {
                NonNullList<ItemStack> inputList = ((AlloyRecipe) recipe).getInputs();
                inputs.addAll(inputList);
            }
        }
        return inputs;
    }

    public final IItemHandlerModifiable getInventory(){
        return this.inventory;
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
