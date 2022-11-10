package net.jmb19905.medievalworlds.client.renderers.blockentity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.block.hearth.HearthBlock;
import net.jmb19905.medievalworlds.common.block.hearth.HearthPartProperty;
import net.jmb19905.medievalworlds.common.blockentities.HearthBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HearthRenderer implements BlockEntityRenderer<HearthBlockEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/hearth.png");

    private final List<ModelPart> coalParts;

    private final List<ModelPart> coalBottomParts;
    private final ItemRenderer itemRenderer;

    public HearthRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        var root = context.bakeLayer(ClientSetup.HEARTH_LAYER);

        var coals = root.getChild("coals");
        coalParts = ImmutableList.of(
                coals.getChild("batch1"),
                coals.getChild("batch2"),
                coals.getChild("batch3"),
                coals.getChild("batch4")
        );

        coalBottomParts = ImmutableList.of(
                coals.getChild("bottom_batch1"),
                coals.getChild("bottom_batch2"),
                coals.getChild("bottom_batch3"),
                coals.getChild("bottom_batch4")
        );
    }

    @Override
    public void render(@NotNull HearthBlockEntity entity, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        BlockState state = entity.getBlockState();
        if (state.getValue(HearthBlock.PART) == HearthPartProperty.MAIN) {
            stack.pushPose();
            Direction facing = state.getValue(HearthBlock.FACING);
            int ew = facing.getAxis() == Direction.Axis.X ? 1 : 0;
            int xOffset = 0;
            int zOffset = 0;
            int e = (facing.getStepX() + 1) / 2;
            int s = (facing.getStepZ() + 1) / 2;
            xOffset += e + s;
            zOffset -= e - ew - s;
            stack.translate(xOffset, 0, zOffset);
            stack.mulPose(Vector3f.ZP.rotationDegrees(180));
            stack.mulPose(Vector3f.YP.rotationDegrees(facing.toYRot()));
            VertexConsumer renderer = bufferSource.getBuffer(RenderType.entitySolid(TEXTURE));
            int coalLevel = entity.getCoalFillLevelInt();
            for (int i = 0; i < coalLevel; i++) {
                coalParts.get(i).render(stack, renderer, light, overlay);
                coalBottomParts.get(i).render(stack, renderer, light, overlay);
            }
            stack.popPose();

            stack.pushPose();
            Vec3 pos = new Vec3(0.5, 0.8125, 0.5).relative(facing.getClockWise(), 0.5);
            stack.translate(pos.x, pos.y, pos.z);
            stack.mulPose(Vector3f.YP.rotationDegrees( (facing.get2DDataValue() - 1 - (facing.get2DDataValue() % 2)) * 90 + 45));
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.scale(.5f, .5f, .5f);
            //this.itemRenderer.renderStatic(new ItemStack(MWItems.HEATED_IRON_INGOT.get()), ItemTransforms.TransformType.FIXED, light, overlay, stack, bufferSource, entity.hashCode());
            stack.popPose();
        }
    }

    public static LayerDefinition createHearthLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition coals = partdefinition.addOrReplaceChild("coals", CubeListBuilder.create(), PartPose.offset(-8.0F, 24.0F, 8.0F));

        PartDefinition batch1 = coals.addOrReplaceChild("batch1", CubeListBuilder.create().texOffs(35, 53).mirror().addBox(11.7654F, -13.0F, -10.1522F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 0.0F));

        batch1.addOrReplaceChild("1", CubeListBuilder.create().texOffs(3, 54).mirror().addBox(-8.8F, -1.0F, -0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -13.0F, -12.0F, 0.0F, 0.7854F, 0.0F));

        batch1.addOrReplaceChild("2", CubeListBuilder.create().texOffs(36, 56).addBox(-0.2F, -1.0F, -0.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8457F, -13.0F, -11.7758F, 0.0F, -0.3927F, 0.0F));

        batch1.addOrReplaceChild("3", CubeListBuilder.create().texOffs(27, 58).mirror().addBox(-7.4F, -1.0F, 0.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -13.0F, -12.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition batch2 = coals.addOrReplaceChild("batch2", CubeListBuilder.create().texOffs(7, 52).mirror().addBox(3.0782F, -12.0F, -7.3994F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        batch2.addOrReplaceChild("4", CubeListBuilder.create().texOffs(56, 60).mirror().addBox(4.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(52, 51).mirror().addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.3024F, -12.0F, -4.2451F, 0.0F, 0.3927F, 0.0F));

        batch2.addOrReplaceChild("5", CubeListBuilder.create().texOffs(24, 54).mirror().addBox(-1.2F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.2263F, -12.0F, -9.8624F, 0.0F, -0.3927F, 0.0F));

        PartDefinition batch3 = coals.addOrReplaceChild("batch3", CubeListBuilder.create().texOffs(27, 53).mirror().addBox(10.9176F, -13.0F, -5.3869F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(2, 56).mirror().addBox(10.9176F, -13.0F, -12.3869F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 55).mirror().addBox(3.5176F, -13.0F, -12.0869F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        batch3.addOrReplaceChild("6", CubeListBuilder.create().texOffs(48, 53).mirror().addBox(-1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.9176F, -12.0F, -9.3869F, 0.0F, 0.3927F, 0.0F));

        PartDefinition batch4 = coals.addOrReplaceChild("batch4", CubeListBuilder.create().texOffs(12, 54).mirror().addBox(7.9176F, -12.001F, -9.3869F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 58).mirror().addBox(8.7176F, -12.001F, -7.3869F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(5.6848F, -12.001F, -4.1643F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        batch4.addOrReplaceChild("7", CubeListBuilder.create().texOffs(27, 51).mirror().addBox(-1.4F, -1.0005F, 0.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.9176F, -12.0005F, -12.3869F, 0.0F, 0.3927F, 0.0F));

        coals.addOrReplaceChild("bottom_batch1", CubeListBuilder.create().texOffs(18, 53).mirror().addBox(2.0F, -5.0F, -6.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 50).addBox(2.0F, -8.0F, -4.0F, 12.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        coals.addOrReplaceChild("bottom_batch2", CubeListBuilder.create().texOffs(43, 59).addBox(2.0F, -5.0F, -10.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(29, 52).mirror().addBox(2.0F, -7.0F, -8.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 53).mirror().addBox(11.5F, -5.0F, -11.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        coals.addOrReplaceChild("bottom_batch3", CubeListBuilder.create().texOffs(18, 58).mirror().addBox(3.0F, -5.0F, -12.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 54).mirror().addBox(7.0F, -5.0F, -10.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 53).mirror().addBox(5.0F, -5.0F, -14.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        coals.addOrReplaceChild("bottom_batch4", CubeListBuilder.create().texOffs(13, 52).mirror().addBox(5.0F, -7.0F, -11.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(18, 57).mirror().addBox(10.0F, -7.0F, -9.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 52).mirror().addBox(9.0F, -5.0F, -14.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
