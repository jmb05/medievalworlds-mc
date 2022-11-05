package net.jmb19905.medievalworlds.client.renderers.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.capability.Hood;
import net.jmb19905.medievalworlds.common.item.CloakItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CloakRenderer implements ICurioRenderer {
    private final ModelPart hood;
    private final ModelPart cape;
    private final ModelPart connection;

    public CloakRenderer() {
        ModelPart modelPart = Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.CLOAK_LAYER);
        this.hood = modelPart.getChild("hood");
        this.cape = modelPart.getChild("cape");
        this.connection = modelPart.getChild("connection");
    }

    public static LayerDefinition createCloakLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cape = partdefinition.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-4.0F, 0.3F, -1.0F, 8.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 50).addBox(-4.0F, -1.0F, -2.001F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(55, 39).addBox(8.857F, 0.299F, -2.2986F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 39).mirror().addBox(-9.857F, 0.299F, -2.2986F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -2.3F, 5.0F));
        cape.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 39).mirror().addBox(-6.0F, -24.0F, -1.0F, 6.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 24.3F, 0.0F, 0.0F, -0.2182F, 0.0F));
        cape.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 39).addBox(0.0F, -24.0F, -1.0F, 6.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 24.3F, 0.0F, 0.0F, 0.2182F, 0.0F));

        PartDefinition side_part_left = cape.addOrReplaceChild("side_part_left", CubeListBuilder.create(), PartPose.offset(9.8586F, 22.9149F, -7.7382F));
        side_part_left.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(54, 51).addBox(-1.0011F, -20.8369F, -0.2549F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 51).addBox(-1.0011F, -18.8369F, -0.5549F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 51).addBox(-1.0008F, -15.8369F, 0.2451F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
        side_part_left.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(54, 51).addBox(-1.0008F, -12.8369F, 0.2451F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
        side_part_left.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(57, 13).addBox(-1.001F, -3.0369F, 4.6451F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 21).addBox(-1.0012F, -5.9379F, 4.0451F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 0).addBox(-1.001F, -8.8369F, 0.2451F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition side_part_right = cape.addOrReplaceChild("side_part_right", CubeListBuilder.create(), PartPose.offset(-9.8586F, 22.9149F, -7.7382F));
        side_part_right.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(54, 51).mirror().addBox(0.0012F, -20.8369F, -0.2549F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 51).mirror().addBox(0.0011F, -18.8369F, -0.5549F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 51).mirror().addBox(0.0011F, -15.8369F, 0.2451F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
        side_part_right.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(54, 51).mirror().addBox(0.0011F, -12.8369F, 0.2451F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
        side_part_right.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(57, 13).mirror().addBox(0.001F, -3.0369F, 4.6451F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 21).mirror().addBox(0.0012F, -5.9379F, 4.0451F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(38, 0).mirror().addBox(0.0011F, -8.8369F, 0.2451F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition connection = partdefinition.addOrReplaceChild("connection", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.0003F, 0.0F, -0.4984F, 1.0F, 24.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(18.7159F, 0.0F, -0.4984F, 1.0F, 24.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(5.8578F, -1.3F, 5.7993F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(8.8578F, 3.0F, -0.6958F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(5.8578F, -1.3F, 2.8004F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.8578F, -2.0F, -2.8003F));
        connection.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(31, 23).mirror().addBox(-4.0F, 0.0F, 0.5023F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(31, 23).mirror().addBox(-6.0F, 0.0F, 2.5023F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(46, 16).mirror().addBox(-4.0F, 0.0F, -0.4977F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.7156F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        connection.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(31, 23).addBox(0.0F, 0.0F, 0.5023F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 23).addBox(0.0F, 0.0F, 2.5023F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 16).addBox(0.0F, 0.0F, -0.4977F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        connection.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(48, 13).addBox(-2.0F, -2.0F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0854F, 4.0394F, 0.0033F, 0.0F, 0.0F, 0.1745F));
        connection.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(48, 13).mirror().addBox(-5.0F, -2.0F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(15.6301F, 4.0394F, 0.0042F, 0.0F, 0.0F, -0.1745F));
        connection.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(0.1F, 0.0F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.0002F, -0.001F, 6.5017F, 0.0F, -0.2182F, -0.2182F));
        connection.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 50).addBox(-6.1F, 0.0F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.7158F, -0.001F, 6.5017F, 0.0F, 0.2182F, 0.2182F));

        partdefinition.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 47).addBox(-6.0F, -0.8822F, 2.7039F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 44).addBox(-6.0F, -9.8822F, 4.7039F, 12.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).mirror().addBox(-6.0F, -8.8822F, -4.2961F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 21).addBox(-5.999F, -9.883F, -4.297F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(5.0F, -8.8822F, -4.2961F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();

        VertexConsumer vertexConsumer;
        if(stack.getItem() instanceof CloakItem cloakItem) {
            vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entitySolid(cloakItem.getRenderTexture()));
        } else return;

        LivingEntity entity = slotContext.entity();

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> model) {
            this.hood.xRot = model.head.xRot;
            this.hood.yRot = model.head.yRot;
            this.hood.zRot = model.head.zRot;
        } else {
            this.hood.xRot = (float) Math.toRadians(headPitch);
            this.hood.yRot = (float) Math.toRadians(netHeadYaw);
        }
        entity.getCapability(Hood.HOOD_CAPABILITY).ifPresent(iHood -> {
            if(iHood.isHoodDown()) {
                this.hood.xRot = (float) Math.toRadians(-90);
                this.hood.yRot = (float) Math.toRadians(0);
            }
        });

        if(entity instanceof Player player) {
            double deltaX = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
            double deltaY = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
            double deltaZ = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
            float deltaYRot = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
            double sinDeltaYRot = Mth.sin((float) Math.toRadians(deltaYRot));
            double negCosDeltaYRot = -Mth.cos((float) Math.toRadians(deltaYRot));
            float deltaY10 = (float) deltaY * 10.0F;
            deltaY10 = Mth.clamp(deltaY10, -6.0F, 32.0F);
            float f2 = (float) (deltaX * sinDeltaYRot + deltaZ * negCosDeltaYRot) * 100.0F;
            f2 = Mth.clamp(f2, 0.0F, 150.0F);
            f2 = Math.max(f2, 0f);

            float bob = Mth.lerp(partialTicks, player.oBob, player.bob);
            float walkDist = Mth.lerp(partialTicks, player.walkDistO, player.walkDist);
            deltaY10 += Mth.sin(walkDist * 6.0F) * 32.0F * bob;
            if (player.isCrouching()) {
                deltaY10 += 45.0F;
                matrixStack.translate(0, 0.25, 0);
            }

            float capeXRotDeg = Mth.clamp((6f + f2 / 2f + deltaY10) * 0.7f, 0, 15);
            this.cape.xRot = (float) Math.toRadians(capeXRotDeg);
        }

        this.hood.render(matrixStack, vertexConsumer, light, 655360);
        this.cape.render(matrixStack, vertexConsumer, light, 655360);
        this.connection.render(matrixStack, vertexConsumer, light, 655360);
        matrixStack.popPose();
    }
}
