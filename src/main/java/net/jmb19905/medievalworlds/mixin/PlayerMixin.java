package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.jmb19905.medievalworlds.common.item.QuiverItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Predicate;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getAllSupportedProjectiles()Ljava/util/function/Predicate;", shift = At.Shift.AFTER), cancellable = true)
    public void getProjectile(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        Predicate<ItemStack> predicate = ((ProjectileWeaponItem)stack.getItem()).getAllSupportedProjectiles();
        CuriosApi.getCuriosHelper().findFirstCurio((Player) (Object) this, curio -> curio.getItem() instanceof QuiverItem).ifPresent(slotResult -> {
            ItemStack curiosStack = slotResult.stack();
            QuiverInv inv = MWCapabilityManager.retrieveCapability(curiosStack, QuiverInv.QUIVER_INV_CAPABILITY);
            if (inv == null) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
            ItemStack returnVal = inv.getProjectile(predicate);
            returnVal = net.minecraftforge.common.ForgeHooks.getProjectile((Player) (Object) this, stack, returnVal);
            cir.setReturnValue(returnVal);
        });
    }
}