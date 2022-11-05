package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HeatedItem extends Item {

    private final Item baseItem;

    public HeatedItem(Item baseItem, Properties properties) {
        super(properties);
        this.baseItem = baseItem;
    }

    public Item getBaseItem() {
        return baseItem;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide) {
            BlockHitResult hitResult = MWItemHelper.rayTraceBlocks(level, player, ClipContext.Fluid.ANY, Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).getValue());
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() == Blocks.WATER) {
                ItemStack newStack = new ItemStack(((HeatedItem) stack.getItem()).getBaseItem(), stack.getCount());
                player.setItemInHand(hand, newStack);
                NetworkStartupCommon.simpleChannel.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 50, level.dimension())), new SteamEffectPacket(pos, 1));
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

}