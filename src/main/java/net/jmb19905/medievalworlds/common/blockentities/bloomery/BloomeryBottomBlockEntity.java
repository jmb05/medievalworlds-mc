package net.jmb19905.medievalworlds.common.blockentities.bloomery;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.BloomeryMenu;
import net.jmb19905.medievalworlds.common.block.bloomery.BloomeryBlockBottom;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class BloomeryBottomBlockEntity extends AbstractBloomeryBlockEntity implements MenuProvider {

    private Component customName;
    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    public boolean fuelConsumed = false;
    private final CustomItemHandler inventory;
    private int removeCount = 0;
    private int outputCount = 0;

    public BloomeryBottomBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.BLOOMERY_BOTTOM.get(), pos, state);
        this.inventory = new CustomItemHandler(5);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    public Component getName() {
        return customName != null ? customName : this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container." + MedievalWorlds.MOD_ID + ".bloomery");
    }

    public void setCustomName(Component name){
        this.customName = name;
    }

    @Nullable
    public Component getCustomName() {
        return customName;
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        if(nbt.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }

        NonNullList<ItemStack> inventoryAsNonNullList = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventoryAsNonNullList);
        this.inventory.setNonNullList(inventoryAsNonNullList);

        this.currentBurnTime = nbt.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = nbt.getInt("CurrentMaxBurnTime");
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        super.save(nbt);
        if(this.customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
        ContainerHelper.saveAllItems(nbt, this.inventory.toNonNullList());

        nbt.putInt("CurrentBurnTime", currentBurnTime);
        nbt.putInt("CurrentMaxBurnTime", currentMaxBurnTime);
        return nbt;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new BloomeryMenu(windowId, inventory, this);
    }

    public void startHardening() {
        currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1), MWRecipeSerializers.BLOOM_TYPE);
        if(currentMaxBurnTime > 0) {
            currentBurnTime = 0;
            System.out.println("Started Hardening");
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(blockEntity instanceof BloomeryBottomBlockEntity entity) {
            BlockPos topPos = pos.above();
            assert level != null;
            if (level.getBlockState(topPos).getBlock() != MWBlocks.BLOOMERY_CLAY_TOP_BLOCK.get()) {
                level.destroyBlock(pos, true);
            }

            boolean dirty = false;

            CustomItemHandler inventory = (CustomItemHandler) entity.getInventory();

            ItemStack fuelStack = inventory.getStackInSlot(1);

            System.out.println(entity.currentBurnTime + " -> " + entity.currentMaxBurnTime);

            if (state.getValue(BloomeryBlockBottom.CLAY)) {
                if(entity.currentBurnTime < entity.currentMaxBurnTime && entity.currentBurnTime > -1) {
                    if(!entity.fuelConsumed) {
                        inventory.setStackInSlot(1, ItemStack.EMPTY);
                        entity.fuelConsumed = true;
                        System.out.println("Consumed hardening fuel");
                    }
                    entity.currentBurnTime++;
                    if (!state.getValue(BloomeryBlockBottom.LIT)) state.setValue(BloomeryBlockBottom.LIT, true);
                    dirty = true;
                }else if(entity.currentBurnTime == entity.currentMaxBurnTime) {
                    if (state.getValue(BloomeryBlockBottom.LIT)) state.setValue(BloomeryBlockBottom.LIT, false);
                    state.setValue(BloomeryBlockBottom.CLAY, false);
                    entity.currentBurnTime = -1;
                    dirty = true;
                    System.out.println("Bloomery Hardened: " + state.getValue(BloomeryBlockBottom.CLAY));
                }
            } else {

                ItemStack inputStack = inventory.getStackInSlot(0);

                BloomRecipe recipe = entity.getRecipe(inputStack);

                if (recipe != null) {
                    if (entity.currentBurnTime < entity.currentMaxBurnTime && entity.currentBurnTime > -1) {
                        if(!entity.fuelConsumed) {
                            entity.removeCount = Math.min(fuelStack.getCount(), inputStack.getCount());
                            entity.outputCount = entity.removeCount / recipe.getInput().getCount() * recipe.getResultItem().getCount();
                            fuelStack.shrink(entity.removeCount);
                            inputStack.shrink(entity.removeCount);

                            ItemStack visualStack = inputStack.copy();
                            visualStack.setCount(entity.removeCount);
                            inventory.setStackInSlot(5, visualStack);

                            entity.fuelConsumed = true;
                            System.out.println("Consumed Fuel and Input");
                        }
                        entity.currentBurnTime++;
                        if (!state.getValue(BloomeryBlockBottom.LIT)) state.setValue(BloomeryBlockBottom.LIT, true);
                        dirty = true;
                    } else if (entity.currentMaxBurnTime > 0 && entity.currentBurnTime == entity.currentMaxBurnTime) {
                        ItemStack result = recipe.getResultItem().copy();
                        result.setCount(entity.outputCount);
                        inventory.setStackInSlot(2, result);
                        inventory.setStackInSlot(5, ItemStack.EMPTY);
                        int secCount = 0;
                        for (int i = 0; i < entity.removeCount; i++) {
                            if (level.getRandom().nextFloat() > recipe.getSecondaryChance()) secCount++;
                        }
                        if (secCount > 0) {
                            ItemStack secondaryResultStack = recipe.getSecondaryOutput().copy();
                            secondaryResultStack.setCount(Math.min(secondaryResultStack.getCount() * secCount, 64));
                            inventory.setStackInSlot(3, secondaryResultStack);
                        }
                        entity.fuelConsumed = false;
                        entity.currentBurnTime = -1;
                        state.setValue(BloomeryBlockBottom.LIT, false);
                        dirty = true;
                    } else {
                        entity.currentMaxBurnTime = ForgeHooks.getBurnTime(fuelStack, MWRecipeSerializers.BLOOM_TYPE);
                        if (entity.currentMaxBurnTime > 0) {
                            entity.currentBurnTime = 0;
                        }
                        dirty = true;
                    }
                } else if (entity.currentBurnTime < entity.currentMaxBurnTime) {
                    if (state.getValue(BloomeryBlockBottom.LIT)) state.setValue(BloomeryBlockBottom.LIT, false);
                    if (!inventory.getStackInSlot(5).isEmpty()) inventory.setStackInSlot(5, ItemStack.EMPTY);
                    dirty = true;
                }
            }

            if(dirty) {
                entity.setChanged();
                level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
    }

    @Nullable
    private BloomRecipe getRecipe(ItemStack input){
        if(input == null){
            return null;
        }
        Set<Recipe<?>> recipes = Util.findRecipeByType(MWRecipeSerializers.BLOOM_TYPE, this.level);
        for(Recipe<?> iRecipe : recipes){
            BloomRecipe recipe = (BloomRecipe)iRecipe;
            assert this.level != null;
            if(recipe.matches(new RecipeWrapper(inventory), this.level)){
                return recipe;
            }
        }
        return null;
    }

    public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level level){
        Set<ItemStack> inputs = new HashSet<>();
        Set<Recipe<?>> recipes = Util.findRecipeByType(typeIn, level);
        for(Recipe<?> recipe : recipes){
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
