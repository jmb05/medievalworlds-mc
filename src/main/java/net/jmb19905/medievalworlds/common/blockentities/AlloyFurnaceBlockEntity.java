package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.AlloyFurnaceBlock;
import net.jmb19905.medievalworlds.client.menus.AlloyFurnaceMenu;
import net.jmb19905.medievalworlds.common.recipes.AlloyRecipe;
import net.jmb19905.medievalworlds.common.registries.RecipeSerializerRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.BlockEntityTypeRegistryHandler;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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

public class AlloyFurnaceBlockEntity extends BlockEntity implements MenuProvider {

    public int currentAlloyTime = 0;
    //TODO: check why alloy time and burn time do not align
    public final int maxAlloyTime = 200 - 2;
    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    private int lastValidMaxBurnTime = -1;
    public boolean fuelConsumed = false;
    private final CustomItemHandler inventory;
    private Component customName;

    public AlloyFurnaceBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        this.inventory = new CustomItemHandler(4);
    }

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntityTypeRegistryHandler.ALLOY_FURNACE.get(), pos, state);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, @Nonnull Inventory playerInv, @Nonnull Player player) {
        return new AlloyFurnaceMenu(windowID, playerInv, this);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        boolean dirty = false;
        if(blockEntity instanceof AlloyFurnaceBlockEntity alloyFurnaceBlockEntity) {
            if (level != null && !level.isClientSide) {
                boolean flipped = false;
                CustomItemHandler inventory = alloyFurnaceBlockEntity.inventory;
                ItemStack inv0 = inventory.getStackInSlot(0);
                ItemStack inv1 = inventory.getStackInSlot(1);
                AlloyRecipe recipe = alloyFurnaceBlockEntity.getRecipe(inv0, inv1, false);
                if (recipe == null) {
                    AlloyRecipe flippedRecipe = alloyFurnaceBlockEntity.getRecipe(inv1, inv0, true);
                    if (flippedRecipe != null) {
                        recipe = flippedRecipe;
                        flipped = true;
                    }
                }
                if (recipe != null) {
                    if (alloyFurnaceBlockEntity.currentBurnTime < alloyFurnaceBlockEntity.currentMaxBurnTime && alloyFurnaceBlockEntity.currentBurnTime >= 0 && inventory.getStackInSlot(3).getCount() < 64) {
                        if (!alloyFurnaceBlockEntity.fuelConsumed) {
                            inventory.decrStackSize(2, 1);
                            alloyFurnaceBlockEntity.fuelConsumed = true;
                        }
                        if (alloyFurnaceBlockEntity.currentAlloyTime < alloyFurnaceBlockEntity.maxAlloyTime) {
                            if (!level.getBlockState(pos).getValue(AlloyFurnaceBlock.LIT)) {
                                level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, true));
                            }
                            alloyFurnaceBlockEntity.currentAlloyTime++;
                        } else {
                            alloyFurnaceBlockEntity.currentAlloyTime = 0;
                            ItemStack output = Objects.requireNonNull(recipe).getResultItem();
                            inventory.insertItem(3, output.copy(), false);
                            if (flipped) {
                                inventory.decrStackSize(1, recipe.getInput1().getCount());
                                inventory.decrStackSize(0, recipe.getInput2().getCount());
                            } else {
                                inventory.decrStackSize(0, recipe.getInput1().getCount());
                                inventory.decrStackSize(1, recipe.getInput2().getCount());
                            }
                        }
                        dirty = true;
                    } else if (ForgeHooks.getBurnTime(inventory.getStackInSlot(2), RecipeSerializerRegistryHandler.ALLOY_TYPE) > 0 && inventory.getStackInSlot(3).getCount() < 64) {
                        alloyFurnaceBlockEntity.currentBurnTime = 0;
                        alloyFurnaceBlockEntity.fuelConsumed = false;
                        alloyFurnaceBlockEntity.currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(2), RecipeSerializerRegistryHandler.ALLOY_TYPE);
                        dirty = true;
                    } else {
                        alloyFurnaceBlockEntity.currentBurnTime = -1;
                        alloyFurnaceBlockEntity.fuelConsumed = false;
                        level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, false));
                        if (alloyFurnaceBlockEntity.currentAlloyTime > 0) {
                            alloyFurnaceBlockEntity.currentAlloyTime -= 2;
                        }
                        dirty = true;
                    }
                } else {
                    if (level.getBlockState(pos).getValue(AlloyFurnaceBlock.LIT) && alloyFurnaceBlockEntity.currentBurnTime >= alloyFurnaceBlockEntity.currentMaxBurnTime) {
                        level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, false));
                    }
                    if (alloyFurnaceBlockEntity.currentAlloyTime < alloyFurnaceBlockEntity.maxAlloyTime && alloyFurnaceBlockEntity.currentAlloyTime > 0) {
                        alloyFurnaceBlockEntity.currentAlloyTime -= 2;
                    }
                }
                if (alloyFurnaceBlockEntity.currentBurnTime < alloyFurnaceBlockEntity.currentMaxBurnTime && alloyFurnaceBlockEntity.currentBurnTime >= 0) {
                    alloyFurnaceBlockEntity.currentBurnTime++;
                    dirty = true;
                }
                if (alloyFurnaceBlockEntity.currentAlloyTime < 0) {
                    alloyFurnaceBlockEntity.currentAlloyTime = 0;
                    dirty = true;
                }
            }
            if(dirty){
                //alloyFurnaceBlockEntity.markDirty();
                level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
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

    private Component getDefaultName() {
        return new TranslatableComponent("container." + MedievalWorlds.MOD_ID + ".alloy_furnace");
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        if(nbt.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }

        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);

        this.currentAlloyTime = nbt.getInt("CurrentAlloyTime");
        this.currentBurnTime = nbt.getInt("CurrentBurnTime");
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        super.save(nbt);
        if(this.customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());

        nbt.putInt("CurrentAlloyTime", currentAlloyTime);
        nbt.putInt("CurrentBurnTime", currentBurnTime);

        return nbt;
    }

    @Nullable
    private AlloyRecipe getRecipe(ItemStack input1, ItemStack input2, boolean flipped){
        if(input1 == null || input2 == null){
            return null;
        }
        Set<Recipe<?>> recipes = findRecipeByType(RecipeSerializerRegistryHandler.ALLOY_TYPE, this.level);
        for(Recipe<?> iRecipe : recipes){
            AlloyRecipe recipe = (AlloyRecipe)iRecipe;
            assert this.level != null;
            if(recipe.matchesUniversally(new RecipeWrapper(inventory), this.level, flipped)){
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

    public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level worldIn){
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = findRecipeByType(typeIn, worldIn);
        for(Recipe<?> recipe : recipes){
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
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
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

    private boolean itemsAvailable(){
        return this.inventory.getStackInSlot(0).getItem() != Items.AIR && this.inventory.getStackInSlot(1).getItem() != Items.AIR;
    }

    public boolean isBurning(){
        return currentBurnTime < currentMaxBurnTime;
    }
}
