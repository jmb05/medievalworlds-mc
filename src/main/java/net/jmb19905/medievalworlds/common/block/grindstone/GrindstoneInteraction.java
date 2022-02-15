package net.jmb19905.medievalworlds.common.block.grindstone;

import net.jmb19905.medievalworlds.common.recipes.grind.GrindRecipe;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.BlockInteraction;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.Set;

public interface GrindstoneInteraction extends BlockInteraction {

    GrindstoneInteraction GRIND_ITEM = (state, level, pos, player, hand, stack) -> grindItem(level, pos, player, stack);

    static InteractionResult grindItem(Level level, BlockPos pos, Player player, ItemStack stack){
        if(!level.isClientSide) {
            Set<Recipe<?>> recipes = Util.findRecipeByType(MWRecipeSerializers.GRIND_TYPE, level);
            GrindRecipe recipe = (GrindRecipe) recipes
                    .stream()
                    .filter((rec) -> rec instanceof GrindRecipe)
                    .filter((rec) -> ((GrindRecipe) rec).getInput().getItem() == stack.getItem())
                    .findFirst()
                    .orElse(null);
            if(recipe != null) {
                System.out.println("Grind Recipe: " + recipe);
                stack.shrink(1);
                givePlayerItem((ServerPlayer) player, recipe.getResultItem().copy());
                level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1,level.getRandom().nextFloat() * 0.1F + 0.9F);
            }else {
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    static void givePlayerItem(ServerPlayer serverPlayer, ItemStack stack) {
        boolean flag = serverPlayer.getInventory().add(stack);
        if (flag && stack.isEmpty()) {
            stack.setCount(1);
            ItemEntity itementity1 = serverPlayer.drop(stack, false);
            if (itementity1 != null) {
                itementity1.makeFakeItem();
            }

            serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            serverPlayer.containerMenu.broadcastChanges();
        } else {
            ItemEntity itementity = serverPlayer.drop(stack, false);
            if (itementity != null) {
                itementity.setNoPickUpDelay();
                itementity.setOwner(serverPlayer.getUUID());
            }
        }
    }

}
