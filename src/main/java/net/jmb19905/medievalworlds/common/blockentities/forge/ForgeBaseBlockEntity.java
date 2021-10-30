package net.jmb19905.medievalworlds.common.blockentities.forge;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.forge.ForgeBaseBlock;
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
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ForgeBaseBlockEntity extends AbstractForgeBlockEntity implements MenuProvider {

    private final CustomItemHandler inventory;

    public int currentBurnTime = 0;
    public int currentMaxBurnTime = 0;
    public int currentHeatTime = 0;
    public int maxHeatTime = 200;
    public boolean fuelConsumed = false;

    private Component customName;

    public ForgeBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = new CustomItemHandler(5);
    }

    public ForgeBaseBlockEntity(BlockPos pos, BlockState state) {
        this(MWBlockEntityTypes.FORGE_BASE.get(), pos, state);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInv, @Nonnull Player player) {
        return null;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (level != null && !level.isClientSide) {//Only do these calculations on the server
            if (blockEntity instanceof ForgeBaseBlockEntity forgeBlockEntity) {
                boolean dirty = false;

                CustomItemHandler inventory = forgeBlockEntity.inventory;
                ForgeRecipe recipe = forgeBlockEntity.getRecipe(inventory.getStackInSlot(1));

                //Checks if there is a recipe for the ingredients in the slots
                if(recipe != null) {
                    //Checks if the fuel burn timer is running
                    if (forgeBlockEntity.currentBurnTime < forgeBlockEntity.currentMaxBurnTime && forgeBlockEntity.currentBurnTime >= 0) {
                        //If not fuel Item has been consumed it is done now
                        if (!forgeBlockEntity.fuelConsumed) {
                            inventory.decreaseStackSize(2, 1);
                            forgeBlockEntity.fuelConsumed = true;
                        }
                        //Checks if the alloy timer is running
                        if (forgeBlockEntity.currentHeatTime < forgeBlockEntity.maxHeatTime) {
                            //turns on the fire (if it isn't already burning)...
                            if (!level.getBlockState(pos).getValue(ForgeBaseBlock.LIT)) {
                                level.setBlockAndUpdate(pos, state.setValue(ForgeBaseBlock.LIT, true));
                            }

                            //...and increases the alloy timer
                            forgeBlockEntity.currentHeatTime++;
                        } else {
                            //Resets the alloy timer
                            forgeBlockEntity.currentHeatTime = 0;
                            //Put the result in the result slot
                            ItemStack output = Objects.requireNonNull(recipe).getResultItem();
                            inventory.insertItem(1, new ItemStack(output.getItem(), inventory.getStackInSlot(1).getCount()), false);
                        }
                        dirty = true;
                    } else if (ForgeHooks.getBurnTime(inventory.getStackInSlot(0), MWRecipeSerializers.FORGE_TYPE) > 0) {
                        //Else If there is a fuel in the fuel slot:
                        //reset the fuel burn timer
                        forgeBlockEntity.currentBurnTime = 0;
                        forgeBlockEntity.fuelConsumed = false;
                        //change the max burn time to the burn time of the fuel
                        forgeBlockEntity.currentMaxBurnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(2), MWRecipeSerializers.FORGE_TYPE);
                        dirty = true;
                    } else {
                        //reset the fuel burn timer to -1 to indicate that the timer has halted
                        forgeBlockEntity.currentBurnTime = -1;
                        forgeBlockEntity.fuelConsumed = false;
                        forgeBlockEntity.currentMaxBurnTime = 0;
                        level.setBlockAndUpdate(pos, state.setValue(ForgeBaseBlock.LIT, false));
                        //decrease alloy timer
                        if (forgeBlockEntity.currentHeatTime > 0) {
                            forgeBlockEntity.currentHeatTime -= 2;
                        }
                        dirty = true;
                    }
                } else {
                    //If there are no valid Ingredients we turn of the fire but only if there is no fuel still burning and...
                    if (level.getBlockState(pos).getValue(ForgeBaseBlock.LIT) && forgeBlockEntity.currentBurnTime >= forgeBlockEntity.currentMaxBurnTime) {
                        level.setBlockAndUpdate(pos, state.setValue(ForgeBaseBlock.LIT, false));
                    }
                    //...we decrease the heat timer
                    if (forgeBlockEntity.currentHeatTime < forgeBlockEntity.maxHeatTime && forgeBlockEntity.currentHeatTime > 0) {
                        forgeBlockEntity.currentHeatTime -= 2;
                    }
                }
                //The increase the fuel timer if fuel is (still) burning
                //But only if the timer is bigger than 0
                if (forgeBlockEntity.currentBurnTime < forgeBlockEntity.currentMaxBurnTime && forgeBlockEntity.currentBurnTime >= 0) {
                    forgeBlockEntity.currentBurnTime++;
                    dirty = true;
                }
                //The alloy timer should not be smaller than 0
                if (forgeBlockEntity.currentHeatTime < 0) {
                    forgeBlockEntity.currentHeatTime = 0;
                    dirty = true;
                }

                //if the block has changed we write the changes to the disk,
                //notify the neighbours and send the update to the client
                if(dirty){
                    forgeBlockEntity.setChanged();
                    level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
        }
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

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return customName;
    }

    public Component getName() {
        return customName != null ? customName : this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container." + MedievalWorlds.MOD_ID + ".forge");
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

        this.currentBurnTime = nbt.getInt("CurrentBurnTime");
        this.currentMaxBurnTime = nbt.getInt("CurrentMaxBurnTime");
        this.currentHeatTime = nbt.getInt("CurrentHeatTime");
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
        nbt.putInt("CurrentHeatTime", currentHeatTime);

        return nbt;
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
}
