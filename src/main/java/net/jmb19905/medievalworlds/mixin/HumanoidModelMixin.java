package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.item.CloakItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @SuppressWarnings("unchecked")
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainArm()Lnet/minecraft/world/entity/HumanoidArm;"))
    private void setupAnim(T living, float limbSwing, float limbSwingAmount, float h, float i, float j, CallbackInfo ci) {
        HumanoidModel<T> instance = (HumanoidModel<T>) (Object) this;
        SlotResult result = CuriosApi.getCuriosHelper().findFirstCurio(living, stack -> stack.getItem() instanceof CloakItem).orElse(null);
        float cloakFactor = result != null ? 0.5f : 1;

        float f = 1.0F;
        if (living.getFallFlyingTicks() > 4) {
            f = (float)living.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        instance.rightArm.xRot = Mth.cos((limbSwing * cloakFactor) * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        instance.rightArm.xRot *= cloakFactor;
        instance.leftArm.xRot = Mth.cos((limbSwing * cloakFactor) * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        instance.leftArm.xRot *= cloakFactor;
        instance.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        instance.rightLeg.xRot *= cloakFactor;
        instance.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        instance.leftLeg.xRot *= cloakFactor;
        if (instance.riding) {
            instance.rightArm.xRot += (-(float)Math.PI / 5F);
            instance.leftArm.xRot += (-(float)Math.PI / 5F);
            instance.rightLeg.xRot = -1.4137167F;
            instance.leftLeg.xRot = -1.4137167F;
        }

    }

}