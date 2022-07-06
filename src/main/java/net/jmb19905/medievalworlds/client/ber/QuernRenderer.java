package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.blockentities.QuernBlockEntity;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import org.jetbrains.annotations.NotNull;

public class QuernRenderer implements BlockEntityRenderer<QuernBlockEntity> {

    private final ModelPart rotating;
    private final ModelPart main;

    private int rotation = 0;

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/quern.png");

    public QuernRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(ClientSetup.QUERN_LAYER);
        rotating = root.getChild("rotating");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotating = partdefinition.addOrReplaceChild("rotating", CubeListBuilder.create().texOffs(0, 11).addBox(-6.0F, -4.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-3.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-3.0F, -4.0F, 1.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(3.0F, -4.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        rotating.addOrReplaceChild("sub_cube", CubeListBuilder.create().texOffs(4, 4).addBox(-6.5F, -8.001F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -10.001F, 4.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(-2.0F, -8.001F, 3.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(3.5F, -8.001F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(-2.0F, -8.001F, -6.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -8.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -4.0F, -8.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -4.0F, 0.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -4.0F, 0.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void render(@NotNull QuernBlockEntity entity, float partialsTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
        poseStack.translate(0.5,-1.5,-0.5);
        if(entity.isTurning()) rotation++;
        if(rotation >= 360) rotation = 0;
        System.out.println(overlay);
        main.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
        rotating.yRot = (float) Math.toRadians(rotation);
        rotating.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
        poseStack.popPose();
    }
}
