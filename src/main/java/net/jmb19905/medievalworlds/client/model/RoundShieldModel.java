package net.jmb19905.medievalworlds.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RoundShieldModel extends Model {

	private final ModelPart roundShield;

	public RoundShieldModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.roundShield = root.getChild("roundShield");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition roundShieldPD = partdefinition.addOrReplaceChild("roundShield", CubeListBuilder.create().texOffs(17, 0).addBox(-3.0F, -16.0F, 0.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 1).addBox(-5.0F, -15.0F, 0.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(3.0F, -15.0F, 0.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(25, 3).addBox(5.0F, -14.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 5).addBox(-7.0F, -13.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 3).addBox(-6.0F, -14.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 5).addBox(6.0F, -13.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 9).addBox(7.0F, -11.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 9).addBox(-8.0F, -11.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -11.0F, -1.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -10.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -10.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 9).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(21, 4).addBox(-0.5F, -11.0F, 1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 5).addBox(2.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 8).addBox(-3.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-3.0F, -0.9F, -0.1F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-3.0F, -16.1F, -0.1F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-8.1F, -11.0F, -0.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(7.1F, -11.0F, -0.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		roundShieldPD.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, 0.0F, -0.1F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.4363F));
		roundShieldPD.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -0.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		roundShieldPD.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -4.0F, -0.1F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -11.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		roundShieldPD.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, 0.0F, -0.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -16.0F, 0.0F, 0.0F, 0.0F, 0.4363F));
		roundShieldPD.addOrReplaceChild("5", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, 0.0F, -0.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.7053F));
		roundShieldPD.addOrReplaceChild("6", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, 0.0F, -0.1F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		roundShieldPD.addOrReplaceChild("7", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -4.0F, -0.1F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.4363F));
		roundShieldPD.addOrReplaceChild("8", CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, 0.0F, -0.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -16.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		roundShield.render(poseStack, buffer, packedLight, packedOverlay);
	}
}