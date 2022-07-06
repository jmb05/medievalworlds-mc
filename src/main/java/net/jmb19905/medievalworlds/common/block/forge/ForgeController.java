package net.jmb19905.medievalworlds.common.block.forge;

import it.zerono.mods.zerocore.lib.multiblock.AbstractMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ForgeController extends AbstractMultiblockController<ForgeController> {

    protected ForgeController(Level world) {
        super(world);
    }

    @Override
    protected void onPartAdded(@NotNull IMultiblockPart<ForgeController> iMultiblockPart) {

    }

    @Override
    protected void onPartRemoved(@NotNull IMultiblockPart<ForgeController> iMultiblockPart) {

    }

    @Override
    protected void onMachineRestored() {

    }

    @Override
    protected void onMachinePaused() {

    }

    @Override
    protected void onMachineDisassembled() {

    }

    @Override
    protected int getMinimumNumberOfPartsForAssembledMachine() {
        return 0;
    }

    @Override
    protected int getMaximumXSize() {
        return 3;
    }

    @Override
    protected int getMaximumZSize() {
        return 3;
    }

    @Override
    protected int getMaximumYSize() {
        return 3;
    }

    @Override
    protected boolean isMachineWhole(@NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected void onAssimilate(@NotNull IMultiblockController<ForgeController> iMultiblockController) {

    }

    @Override
    protected void onAssimilated(@NotNull IMultiblockController<ForgeController> iMultiblockController) {

    }

    @Override
    protected boolean updateServer() {
        return false;
    }

    @Override
    protected void updateClient() {

    }

    @Override
    protected boolean isBlockGoodForFrame(@NotNull Level level, int i, int i1, int i2, @NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForTop(@NotNull Level level, int i, int i1, int i2, @NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(@NotNull Level level, int i, int i1, int i2, @NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForSides(@NotNull Level level, int i, int i1, int i2, @NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(@NotNull Level level, int i, int i1, int i2, @NotNull IMultiblockValidator iMultiblockValidator) {
        return false;
    }
}
