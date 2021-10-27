package net.jmb19905.medievalworlds.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nonnull;

public class KnightArmorHelmetModel extends CustomArmorModel{

	public KnightArmorHelmetModel(ModelPart root) {
		super(root, EquipmentSlot.HEAD);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		CubeDeformation deformation = new CubeDeformation(.5f);
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0,0).addBox(-4, -8, -4, 8, 8, 8, deformation)
				.texOffs(0, 16).addBox(-4.5F, -6.0F, -4.5F, 9.0F, 5.0F, 9.0F, deformation)
				.texOffs(6, 26).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F, deformation), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 26.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 26.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 36.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 36.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
	}
}