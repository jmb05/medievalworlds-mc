package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.BloomeryMenu;
import net.jmb19905.medievalworlds.common.recipes.bloom.BloomRecipe;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
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

public abstract class BloomeryBlockEntity extends BlockEntity {

    public BloomeryBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);

    }

    public static class Bottom extends BloomeryBlockEntity implements MenuProvider{

        private Component customName;
        public int currentSmeltTime = 0;
        public final int maxSmeltTime = 200 - 2;
        public int currentBurnTime = 0;
        public int currentMaxBurnTime = 0;
        private int lastValidMaxBurnTime = -1;
        public boolean fuelConsumed = false;
        private final CustomItemHandler inventory;

        public Bottom(BlockPos pos, BlockState state) {
            super(MWBlockEntityTypes.BLOOMERY_BOTTOM.get(), pos, state);
            this.inventory = new CustomItemHandler(2);
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

            this.currentSmeltTime = nbt.getInt("CurrentSmeltTime");
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

            nbt.putInt("CurrentSmeltTime", currentSmeltTime);
            nbt.putInt("CurrentBurnTime", currentBurnTime);
            return nbt;
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory inventory, @Nonnull Player player) {
            return new BloomeryMenu(windowId, inventory, this);
        }

        public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
            BlockPos topPos = pos.above();
            assert level != null;
            if (level.getBlockState(topPos).getBlock() != MWBlocks.BLOOMERY_TOP_BLOCK.get()) {
                level.destroyBlock(pos, true);
            }
        }

        @Nullable
        private BloomRecipe getRecipe(ItemStack input){
            if(input == null){
                return null;
            }
            Set<Recipe<?>> recipes = findRecipeByType(MWRecipeSerializers.BLOOM_TYPE, this.level);
            for(Recipe<?> iRecipe : recipes){
                BloomRecipe recipe = (BloomRecipe)iRecipe;
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

        public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> typeIn, Level level){
            Set<ItemStack> inputs = new HashSet<>();
            Set<Recipe<?>> recipes = findRecipeByType(typeIn, level);
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

    public static class Top extends BloomeryBlockEntity {

        public Top(BlockPos pos, BlockState state) {
            super(MWBlockEntityTypes.BLOOMERY_TOP.get(), pos, state);
        }

        public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
            BlockPos bottomPos = pos.below();
            assert level != null;
            if(level.getBlockState(bottomPos).getBlock() != MWBlocks.BLOOMERY_BOTTOM_BLOCK.get()){
                level.destroyBlock(pos, false);
            }
        }

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
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

}
