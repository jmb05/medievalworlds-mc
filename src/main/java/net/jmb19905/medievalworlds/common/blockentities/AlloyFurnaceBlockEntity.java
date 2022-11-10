package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.AlloyFurnaceBlock;
import net.jmb19905.medievalworlds.common.blockentities.abstr.MWNamedInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.jmb19905.medievalworlds.common.menus.AlloyFurnaceMenu;
import net.jmb19905.medievalworlds.common.recipes.MWRecipeHelper;
import net.jmb19905.medievalworlds.common.recipes.AlloyRecipe;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

public class AlloyFurnaceBlockEntity extends MWNamedInventoryBlockEntity {

    public int currentAlloyTime = 0;
    public final int maxAlloyTime = 200 - 2;
    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;

    public AlloyFurnaceBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, 4, pos, state);
    }

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state){
        this(MWBlockEntityTypes.ALLOY_FURNACE.get(), pos, state);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, @Nonnull Inventory playerInv, @Nonnull Player player) {
        return new AlloyFurnaceMenu(windowID, playerInv, this);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (level != null && !level.isClientSide) {//Only do these calculations on the server
            if(blockEntity instanceof AlloyFurnaceBlockEntity alloyFurnace) {
                boolean dirty = false;

                //Get the recipe instance for the items in the slots
                MWItemHandler inventory = alloyFurnace.inventory;
                AlloyRecipeWrapper recipeWrapper = getRecipe(alloyFurnace, inventory);
                AlloyRecipe recipe = recipeWrapper.recipe();
                boolean flipped = recipeWrapper.flipped();

                //Checks if there is a recipe for the ingredients in the slots
                if (recipe != null) {
                    //Checks if the fuel burn timer is running and the output slot isn't clogged
                    if (alloyFurnace.currentBurnTime < alloyFurnace.currentMaxBurnTime && alloyFurnace.currentBurnTime >= 0 && inventory.getStackInSlot(3).getCount() < 64) {
                        //If not fuel Item has been consumed it is done now
                        if (!alloyFurnace.fuelConsumed) {
                            inventory.decreaseStackSize(2, 1);
                            alloyFurnace.fuelConsumed = true;
                        }
                        //Checks if the alloy timer is running
                        if (alloyFurnace.currentAlloyTime < alloyFurnace.maxAlloyTime) {
                            //turns on the fire (if it isn't already burning)...
                            if (!level.getBlockState(pos).getValue(AlloyFurnaceBlock.LIT)) {
                                level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, true));
                            }

                            //...and increases the alloy timer
                            alloyFurnace.currentAlloyTime++;
                        } else {
                            //Resets the alloy timer
                            alloyFurnace.currentAlloyTime = 0;
                            //Put the result in the result slot
                            ItemStack output = Objects.requireNonNull(recipe).getResultItem();
                            inventory.insertItem(3, output.copy(), false);
                            //Checks if the recipe was flipped and removes the recipes accordingly
                            if (flipped) {
                                inventory.decreaseStackSize(1, recipe.getInput1().getCount());
                                inventory.decreaseStackSize(0, recipe.getInput2().getCount());
                            } else {
                                inventory.decreaseStackSize(0, recipe.getInput1().getCount());
                                inventory.decreaseStackSize(1, recipe.getInput2().getCount());
                            }
                        }
                        dirty = true;
                    } else if (ForgeHooks.getBurnTime(inventory.getStackInSlot(2), MWRecipeSerializers.ALLOY_TYPE) > 0 && inventory.getStackInSlot(3).getCount() < 64) {
                        //Else If there is a fuel in the fuel slot and the output slot isn't clogged:
                        //reset the fuel burn timer
                        alloyFurnace.currentBurnTime = 0;
                        alloyFurnace.fuelConsumed = false;
                        //change the max burn time to the burn time of the fuel
                        alloyFurnace.currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(2), MWRecipeSerializers.ALLOY_TYPE);
                        dirty = true;
                    } else {
                        //reset the fuel burn timer to -1 to indicate that the timer has halted
                        alloyFurnace.currentBurnTime = -1;
                        alloyFurnace.fuelConsumed = false;
                        alloyFurnace.currentMaxBurnTime = 0;
                        level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, false));
                        //decrease alloy timer
                        if (alloyFurnace.currentAlloyTime > 0) {
                            alloyFurnace.currentAlloyTime -= 2;
                        }
                        dirty = true;
                    }
                } else {
                    //If there are no valid Ingredients we turn of the fire but only if there is no fuel still burning and...
                    if (level.getBlockState(pos).getValue(AlloyFurnaceBlock.LIT) && alloyFurnace.currentBurnTime >= alloyFurnace.currentMaxBurnTime) {
                        level.setBlockAndUpdate(pos, state.setValue(AlloyFurnaceBlock.LIT, false));
                    }
                    //...we decrease the alloy timer
                    if (alloyFurnace.currentAlloyTime < alloyFurnace.maxAlloyTime && alloyFurnace.currentAlloyTime > 0) {
                        alloyFurnace.currentAlloyTime -= 2;
                    }
                }
                //The increase the fuel timer if fuel is (still) burning
                //But only if the timer is bigger than 0
                if (alloyFurnace.currentBurnTime < alloyFurnace.currentMaxBurnTime && alloyFurnace.currentBurnTime >= 0) {
                    alloyFurnace.currentBurnTime++;
                    dirty = true;
                }
                //The alloy timer should not be smaller than 0
                if (alloyFurnace.currentAlloyTime < 0) {
                    alloyFurnace.currentAlloyTime = 0;
                    dirty = true;
                }
                //if the block has changed we write the changes to the disk,
                //notify the neighbours and send the update to the client
                if(dirty){
                    alloyFurnace.setChanged();
                    level.sendBlockUpdated(pos, state, state, 3);
                }
            }
        }
    }

    private static AlloyRecipeWrapper getRecipe(AlloyFurnaceBlockEntity blockEntity, MWItemHandler inventory){
        boolean flipped = false;
        ItemStack inv0 = inventory.getStackInSlot(0);
        ItemStack inv1 = inventory.getStackInSlot(1);
        AlloyRecipe recipe = blockEntity.getRecipe(inv0, inv1, false);
        if (recipe == null) {
            AlloyRecipe flippedRecipe = blockEntity.getRecipe(inv1, inv0, true);
            if (flippedRecipe != null) {
                recipe = flippedRecipe;
                flipped = true;
            }
        }
        return new AlloyRecipeWrapper(recipe, flipped);
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.currentAlloyTime = tag.getInt("CurrentAlloyTime");
        this.currentBurnTime = tag.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = tag.getInt("CurrentMaxBurnTime");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CurrentAlloyTime", currentAlloyTime);
        tag.putInt("CurrentBurnTime", currentBurnTime);
        tag.putInt("CurrentMaxBurnTime", currentMaxBurnTime);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + MedievalWorlds.MOD_ID + ".alloy_furnace");
    }

    @Nullable
    private AlloyRecipe getRecipe(ItemStack input1, ItemStack input2, boolean flipped){
        if(input1 == null || input2 == null){
            return null;
        }
        Set<Recipe<?>> recipes = MWRecipeHelper.findRecipeByType(MWRecipeSerializers.ALLOY_TYPE, this.level);
        for(Recipe<?> iRecipe : recipes){
            AlloyRecipe recipe = (AlloyRecipe)iRecipe;
            assert this.level != null;
            if(recipe.matchesUniversally(new RecipeWrapper(inventory), this.level, flipped)){
                return recipe;
            }
        }
        return null;
    }

    private boolean itemsAvailable(){
        return this.inventory.getStackInSlot(0).getItem() != Items.AIR && this.inventory.getStackInSlot(1).getItem() != Items.AIR;
    }

    public boolean isBurning(){
        return currentBurnTime < currentMaxBurnTime;
    }

    private record AlloyRecipeWrapper(AlloyRecipe recipe, boolean flipped){}
}