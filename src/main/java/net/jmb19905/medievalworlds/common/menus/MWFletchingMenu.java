package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.FletchingTableBlockEntity;
import net.jmb19905.medievalworlds.common.inv.slots.InputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.OutputSlot;
import net.jmb19905.medievalworlds.common.recipes.FletchingRecipe;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.common.registries.VanillaOverride;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.function.Predicate;

public class MWFletchingMenu extends MWInventoryMenu<FletchingTableBlockEntity> {

    private InputSlot headSlot;
    private InputSlot shaftSlot;
    private InputSlot fletchingSlot;
    private OutputSlot outputSlot;

    private FletchingRecipe currentRecipe;

    public MWFletchingMenu(int windowId, Inventory playerInv, FletchingTableBlockEntity blockEntity) {
        super(MWMenuTypes.FLETCHING.get(), windowId, playerInv, blockEntity);
    }

    public MWFletchingMenu(int windowId, Inventory playerInv, FriendlyByteBuf buf) {
        this(windowId, playerInv, getFletchingTableBlockEntity(playerInv, buf));
    }

    @Override
    protected int addInputSlots() {
        headSlot = new InputSlot(blockEntity.getInventory(), 0, 48, 17);
        this.addSlot(headSlot);
        shaftSlot = new InputSlot(blockEntity.getInventory(), 1, 48, 35);
        this.addSlot(shaftSlot);
        fletchingSlot = new InputSlot(blockEntity.getInventory(), 2, 48, 53);
        this.addSlot(fletchingSlot);
        return 3;
    }

    @Override
    protected int addOutputSlots() {
        outputSlot = new OutputSlot(blockEntity.getInventory(), 3, 124, 35, p -> true, this::onTake);
        this.addSlot(outputSlot);
        return 1;
    }

    @Override
    protected void slotsChanged(int slotIndex) {
        if (slotIndex >= 0 && slotIndex <= 2) {
            List<FletchingRecipe> recipes = getRecipes();
            if (recipes.isEmpty()) {
                currentRecipe = null;
                return;
            }
            currentRecipe = recipes.get(0);
            ItemStack resultStack = currentRecipe.assemble(new RecipeWrapper(blockEntity.getInventory()));
            outputSlot.set(resultStack);
            broadcastChanges();
        }
    }

    protected void onTake(Player player, ItemStack stack) {
        if (currentRecipe == null) return;
        stack.onCraftedBy(player.level, player, stack.getCount());
        headSlot.getItem().shrink(1);
        shaftSlot.getItem().shrink(1);
        fletchingSlot.getItem().shrink(1);
        slotsChanged(0);
        slotsChanged(1);
        slotsChanged(2);
        this.access.execute((level, pos) -> this.level.playSound(player, pos, SoundEvents.VILLAGER_WORK_FLETCHER, SoundSource.BLOCKS, 0.75F, this.level.random.nextFloat() * 0.1F + 0.9F));
        broadcastChanges();
    }

    private static FletchingTableBlockEntity getFletchingTableBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        final BlockEntity tileAtPos = MWInventoryMenu.getBlockEntity(playerInv, data);
        if (tileAtPos instanceof FletchingTableBlockEntity) {
            return (FletchingTableBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("BlockEntity is not correct " + tileAtPos);
    }

    @Override
    public Predicate<BlockState> isValidBlockState() {
        return state -> state.is(VanillaOverride.FLETCHING_TABLE.get());
    }

    private List<FletchingRecipe> getRecipes() {
        return this.level.getRecipeManager()
                .getRecipesFor(
                        MWRecipeSerializers.FLETCHING_TYPE,
                        new RecipeWrapper(blockEntity.getInventory()),
                        this.level
                );
    }
}
