package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.item.CloakItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @SuppressWarnings("unchecked")
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V"))
    private void setupAnim(T living, float f, float g, float h, float i, float j, CallbackInfo ci) {
        HumanoidModel<T> instance = (HumanoidModel<T>) (Object) this;
        CuriosApi.getCuriosHelper().findFirstCurio(living, stack -> stack.getItem() instanceof CloakItem).ifPresent(slotResult -> {
            if (slotResult.slotContext().visible()) {
                instance.rightArm.xRot = instance.rightArm.xRot * 0.5f;
                instance.leftArm.xRot = instance.leftArm.xRot * 0.5f;
                instance.rightLeg.xRot = instance.rightLeg.xRot * 0.5f;
                instance.leftLeg.xRot = instance.leftLeg.xRot * 0.5f;
            }
        });
    }

}
