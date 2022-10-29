package net.jmb19905.medievalworlds.client.renderers.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LongbowModel extends Model {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/longbow.png");

    private final ModelPart longbow;
    private final ModelPart bottom;
    private final ModelPart bottom_tip;
    private final ModelPart bottom_string;
    private final ModelPart top;
    private final ModelPart top_tip;
    private final ModelPart top_string;
    private final ModelPart arrow;

    private static final float MIN_STICK_ANGLE = 26.5f;
    private static final float MAX_STICK_ANGLE = 30;
    private static final float MIN_STRING_ANGLE = 53;
    private static final float MAX_STRING_ANGLE = 77.5f;
    private static final float MIN_ARROW_Z = 0;
    private static final float MAX_ARROW_Z = -4.5f;

    public LongbowModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.longbow = root.getChild("longbow");
        this.bottom = this.longbow.getChild("bottom");
        this.bottom_tip = this.bottom.getChild("bottom_tip");
        this.bottom_string = this.bottom_tip.getChild("bottom_string");
        this.top = this.longbow.getChild("top");
        this.top_tip = this.top.getChild("top_tip");
        this.top_string = this.top_tip.getChild("top_string");
        this.arrow = root.getChild("arrow");
    }

    public static LayerDefinition createLongbowLayer() {MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition longbow = partdefinition.addOrReplaceChild("longbow", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bottom = longbow.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(4, 2).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -13.0F, 5.0F, -0.4625F, 0.0F, 0.0F));
        PartDefinition bottom_tip = bottom.addOrReplaceChild("bottom_tip", CubeListBuilder.create().texOffs(8, 2).addBox(-1F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -0.4625F, 0.0F, 0.0F));
        bottom_tip.addOrReplaceChild("bottom_string", CubeListBuilder.create().texOffs(30, -1).addBox(0.0F, -14.0F, 0.0F, 0.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, 0.925F, 0.0F, 0.0F));
        PartDefinition top = longbow.addOrReplaceChild("top", CubeListBuilder.create().texOffs(4, 2).addBox(-1.0F, -10.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -21.0F, 5.0F, 0.4625F, 0.0F, 0.0F));
        PartDefinition top_tip = top.addOrReplaceChild("top_tip", CubeListBuilder.create().texOffs(8, 2).addBox(-1F, -3.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.4625F, 0.0F, 0.0F));
        top_tip.addOrReplaceChild("top_string", CubeListBuilder.create().texOffs(30, -1).addBox(-0F, 0.0F, 0.0F, 0.0F, 14.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, -3.0F, -1.0F, -0.925F, 0.0F, 0.0F));

        longbow.addOrReplaceChild("middle", CubeListBuilder.create()
                .texOffs(16, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(8, 6).addBox(-1F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 4.0F));

        PartDefinition arrow = partdefinition.addOrReplaceChild("arrow", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -2.5F, -7.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.7F, 7.0F, 1.7F, 0.0524F, 0.0524F, 0.7854F));
        arrow.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.5F, -8.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, -1.5708F));
        arrow.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.5F, -8.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void setArrowVisible(boolean b) {
        arrow.visible = b;
    }

    public void setPullback(float d) {
        float stick_angle = (float) Math.toRadians(Mth.lerp(d, MIN_STICK_ANGLE, MAX_STICK_ANGLE));
        float string_angle = (float) Math.toRadians(Mth.lerp(d, MIN_STRING_ANGLE, MAX_STRING_ANGLE));

        bottom.xRot = -stick_angle;
        bottom_tip.xRot = -stick_angle;
        bottom_string.xRot = string_angle;

        top.xRot = stick_angle;
        top_tip.xRot = stick_angle;
        top_string.xRot = -string_angle;

        arrow.z = Mth.lerp(d, 6.3f, 1.5f);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        longbow.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
        arrow.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
