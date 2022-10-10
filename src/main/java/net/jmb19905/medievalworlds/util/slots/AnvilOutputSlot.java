package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;

public class AnvilOutputSlot extends OutputSlot{

    private boolean locked = true;

    public AnvilOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !locked;
    }
}
