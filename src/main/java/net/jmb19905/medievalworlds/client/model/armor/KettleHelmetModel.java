package net.jmb19905.medievalworlds.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import javax.annotation.Nonnull;

public class KettleHelmetModel extends CustomArmorModel{

    public KettleHelmetModel(ModelPart part) {
        super(part);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeDeformation deformation = new CubeDeformation(.5f);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(-12, 0).addBox(-6.0F, -4.01F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 4.0F, 8.0F, deformation)
                .texOffs(24, 0).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 1.0F, 4.0F, deformation), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 26.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 26.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 36.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 36.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 16);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
    }

}
