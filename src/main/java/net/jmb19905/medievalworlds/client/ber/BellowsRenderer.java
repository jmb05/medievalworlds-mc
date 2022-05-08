package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.block.BellowsBlock;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BellowsRenderer implements BlockEntityRenderer<BellowsBlockEntity> {

	private final float rotation = -30;

	private final ModelPart top;
	private final ModelPart bottom;
	private final ModelPart squishyParts;
	private final ModelPart other;

	private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/bellows.png");

	public BellowsRenderer(BlockEntityRendererProvider.Context context) {
		ModelPart root = context.bakeLayer(ClientSetup.BELLOWS_LAYER);
		this.top = root.getChild("top");
		this.bottom = root.getChild("bottom");
		this.squishyParts = root.getChild("squishyParts");
		this.other = root.getChild("other");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.2068F, -4.9542F, 6.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -2.2068F, 6.0458F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -5.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -1.0F, 6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("squishyParts", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(1.5F, -1.1F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(-2.5F, -1.1F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(1.5F, -0.5F, -6.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(-2.5F, -0.5F, -6.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(1.5F, 0.2F, -9.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-2.5F, 0.2F, -9.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(1.5F, -2.0F, -2.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(-2.5F, -2.0F, -2.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.5F, 4.0F, 0.1309F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("other", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -8.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}


	@Override
	public void render(@NotNull BellowsBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
		switch (entity.getBlockState().getValue(BellowsBlock.FACING)) {
			case NORTH -> {
				poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
				poseStack.translate(-.5, -1.5, .5);
			}
			case EAST -> {
				poseStack.mulPose(Vector3f.YP.rotationDegrees(270));
				poseStack.translate(-.5, -1.5, -.5);
			}
			case WEST -> {
				poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
				poseStack.translate(0.5, -1.5, 0.5);
			}
			case SOUTH -> poseStack.translate(0.5, -1.5, -0.5);
		}
		top.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
		bottom.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
		squishyParts.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
		other.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
		poseStack.popPose();
	}
}