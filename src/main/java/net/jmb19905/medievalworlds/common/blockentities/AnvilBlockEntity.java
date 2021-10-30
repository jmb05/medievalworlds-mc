package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class AnvilBlockEntity extends BlockEntity {

    private final CustomItemHandler inventory;
    private final BlockState state;

    public int currentRecipePointer = 0;

    public AnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.state = state;
        this.inventory = new CustomItemHandler(3);
    }

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.CUSTOM_ANVIL.get(), pos, state);
    }

    public Direction getFacingDirection() {
        return state.getValue(AnvilBlock.FACING);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);

        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsList);
        inventory.setNonNullList(inventoryAsList);
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        super.save(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());
        return nbt;
    }

    public final IItemHandlerModifiable getInventory() {
        return inventory;
    }

    public void cycleResultItem() {
        List<AnvilRecipe> recipes = getRecipes(inventory.getStackInSlot(0));
        if(recipes == null || recipes.size() < 1) {
            inventory.setStackInSlot(2, ItemStack.EMPTY);
            return;
        }
        currentRecipePointer++;
        if (currentRecipePointer > recipes.size() - 1 || hasNoResultItem()) {
            currentRecipePointer = 0;
        }
        inventory.setStackInSlot(2, recipes.get(currentRecipePointer).getResultItem());
        System.out.println(getItemToShow());
    }

    public boolean hasNoResultItem() {
        return inventory.getStackInSlot(2).isEmpty();
    }

    public void lockRecipe() {
        if(hasNoResultItem()) {
            return;
        }
        inventory.setStackInSlot(0, inventory.getStackInSlot(2).copy());
        inventory.setStackInSlot(1, ItemStack.EMPTY);
        inventory.setStackInSlot(2, ItemStack.EMPTY);
    }

    public ItemStack getItemToShow(){
        ItemStack inputItem = inventory.getStackInSlot(0);
        ItemStack resultItem = inventory.getStackInSlot(1);
        if(resultItem != ItemStack.EMPTY && resultItem.getItem() != Items.AIR){
            return resultItem;
        }else {
            return inputItem;
        }
    }

    @Nullable
    private List<AnvilRecipe> getRecipes(ItemStack input){
        if(input == null){
            return null;
        }
        Set<Recipe<?>> recipes = Util.findRecipeByType(MWRecipeSerializers.ANVIL_TYPE, this.level);
        List<AnvilRecipe> list = new ArrayList<>();
        for(Recipe<?> iRecipe : recipes){
            AnvilRecipe recipe = (AnvilRecipe) iRecipe;
            assert this.level != null;
            if(recipe.matches(new RecipeWrapper(inventory), this.level)){
                list.add(recipe);
            }
        }
        return list;
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

    public boolean hasItems(){
        return !inventory.getStackInSlot(0).isEmpty();
    }
}
