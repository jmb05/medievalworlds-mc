package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.item.weapon.IMWShieldItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    public double getBlockingAngle() {
        LivingEntity instance = (LivingEntity) (Object) this;
        if (!instance.isBlocking()) return 0;
        if (instance.isUsingItem() && !instance.getUseItem().isEmpty()) {
            Item item = instance.getUseItem().getItem();
            if (item == Items.SHIELD) {
                return 50;
            } else if (item instanceof IMWShieldItem shieldItem) {
                return shieldItem.getBlockAngle();
            }
        }
        return 0;
    }

    @Inject(method = "isDamageSourceBlocked", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;getSourcePosition()Lnet/minecraft/world/phys/Vec3;", shift = At.Shift.BEFORE), cancellable = true)
    private void isDamageSourceBlocked(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity instance = (LivingEntity) (Object) this;
        Vec3 sourcePosition = damageSource.getSourcePosition();
        if (sourcePosition != null) {
            Vec3 targetView = instance.getViewVector(1.0F);
            Vec3 sourceToTarget = sourcePosition.vectorTo(instance.position()).normalize();
            sourceToTarget = new Vec3(sourceToTarget.x, 0.0D, sourceToTarget.z);
            cir.setReturnValue(sourceToTarget.dot(targetView) < Math.cos(Math.toRadians(getBlockingAngle())));
        }
    }
}