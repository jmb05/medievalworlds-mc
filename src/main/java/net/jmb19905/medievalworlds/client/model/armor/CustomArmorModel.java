package net.jmb19905.medievalworlds.client.model.armor;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CustomArmorModel extends HumanoidModel<LivingEntity> {
    protected final EquipmentSlot slot;
    private final List<ModelPart> parts;

    public CustomArmorModel(ModelPart part, EquipmentSlot slot) {
        super(part);
        this.slot = slot;
        this.parts = new ArrayList<>();
    }

    protected void addToRender(ModelPart part) {
        parts.add(part);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        for(ModelPart part : parts) {
            part.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}