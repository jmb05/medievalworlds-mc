package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.jmb19905.medievalworlds.common.item.quiver.QuiverItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @Inject(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getInventory()Lnet/minecraft/world/entity/player/Inventory;"), cancellable = true)
    protected void tryPickupItem(Player player, CallbackInfoReturnable<Boolean> cir) {
        CuriosApi.getCuriosHelper().findFirstCurio(player, stack -> stack.getItem() instanceof QuiverItem).ifPresent(slotResult -> {
            ItemStack quiverStack = slotResult.stack();
            QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
            if (inv == null) return;
            ItemStack pickupStack = getPickupItem();
            int addCount = inv.add(pickupStack);
            if (addCount > 0) {
                player.take((Entity) (Object) this, 1);
                ((Entity) (Object) this).discard();
                cir.setReturnValue(true);
            }
        });
        if (cir.getReturnValue() != null) return;
        cir.setReturnValue(player.getInventory().add(this.getPickupItem()));
    }

    @Shadow
    protected abstract ItemStack getPickupItem();

}
