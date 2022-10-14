package net.jmb19905.medievalworlds.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.IArmPoseTransformer;

public class CustomArmPose implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        if (entity.isBlocking()) {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = (float) Math.toRadians(-50);
                model.rightArm.yRot = (float) Math.toRadians(-40);
                model.rightArm.zRot = (float) Math.toRadians(35);
                model.leftArm.xRot = (float) Math.toRadians(-75);
            } else {
                model.leftArm.xRot = (float) Math.toRadians(-50);
                model.leftArm.yRot = (float) Math.toRadians(40);
                model.leftArm.zRot = (float) Math.toRadians(-35);
                model.rightArm.xRot = (float) Math.toRadians(-75);
            }
        }
    }
}