package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class OutputSlot extends SlotItemHandler {

    private Predicate<Player> takePredicate;
    private BiConsumer<Player, ItemStack> takeAction;

    public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<Player> takePredicate, BiConsumer<Player, ItemStack> takeAction) {
        super(itemHandler, index, xPosition, yPosition);
        this.takePredicate = takePredicate;
        this.takeAction = takeAction;
    }

    public void setTakePredicate(Predicate<Player> takePredicate) {
        this.takePredicate = takePredicate;
    }

    public void setTakeAction(BiConsumer<Player, ItemStack> takeAction) {
        this.takeAction = takeAction;
    }

    @Override
    public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        if (takeAction == null) {
            super.onTake(player, stack);
            return;
        }
        takeAction.accept(player, stack);
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        if (takePredicate == null) return super.mayPickup(playerIn);
        return takePredicate.test(playerIn);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return false;
    }
}
