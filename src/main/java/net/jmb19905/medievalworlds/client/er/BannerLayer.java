package net.jmb19905.medievalworlds.client.er;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;
import java.util.Objects;

public class BannerLayer implements ICurioRenderer {

    private final ImmutableMap<Object, Object> BANNER_COLORS = (ImmutableMap.builder()
            .put(Items.BLACK_BANNER, DyeColor.BLACK)
            .put(Items.BLUE_BANNER, DyeColor.BLUE)
            .put(Items.BROWN_BANNER, DyeColor.BROWN)
            .put(Items.CYAN_BANNER, DyeColor.CYAN)
            .put(Items.GRAY_BANNER, DyeColor.GRAY)
            .put(Items.GREEN_BANNER, DyeColor.GREEN)
            .put(Items.LIGHT_BLUE_BANNER, DyeColor.LIGHT_BLUE)
            .put(Items.LIGHT_GRAY_BANNER, DyeColor.LIGHT_GRAY)
            .put(Items.LIME_BANNER, DyeColor.LIME)
            .put(Items.MAGENTA_BANNER, DyeColor.MAGENTA)
            .put(Items.ORANGE_BANNER, DyeColor.ORANGE)
            .put(Items.PINK_BANNER, DyeColor.PINK)
            .put(Items.PURPLE_BANNER, DyeColor.PURPLE)
            .put(Items.RED_BANNER, DyeColor.RED)
            .put(Items.WHITE_BANNER, DyeColor.WHITE)
            .put(Items.YELLOW_BANNER, DyeColor.YELLOW)
            .build());

    private final ModelPart flag;
    private final ModelPart pole;
    private final ModelPart bar;

    public BannerLayer() {
        ModelPart modelpart = Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.BANNER_LOCATION);
        this.flag = modelpart.getChild("flag");
        this.pole = modelpart.getChild("pole");
        this.bar = modelpart.getChild("bar");
    }

    public static LayerDefinition createBannerLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -56.0F, 7.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -54.0F, 8.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 42).addBox(-10.0F, -56.0F, 8.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.createPatterns((DyeColor) Objects.requireNonNull(BANNER_COLORS.get(stack.getItem())), BannerBlockEntity.getItemPatterns(stack));
        matrixStack.pushPose();
        matrixStack.scale(.4f, .4f, .4f);
        matrixStack.translate(0,-0.3,0);
        VertexConsumer vertexconsumer = ModelBakery.BANNER_BASE.buffer(renderTypeBuffer, RenderType::entitySolid);
        this.flag.xRot = (float) Math.toRadians(headPitch);
        this.flag.yRot = (float) Math.toRadians(netHeadYaw);
        this.pole.xRot = (float) Math.toRadians(headPitch);
        this.pole.yRot = (float) Math.toRadians(netHeadYaw);
        this.bar.xRot = (float) Math.toRadians(headPitch);
        this.bar.yRot = (float) Math.toRadians(netHeadYaw);
        this.pole.render(matrixStack, vertexconsumer, light, 0);
        this.bar.render(matrixStack, vertexconsumer, light, 0);
        renderPatterns(matrixStack, renderTypeBuffer, light, 0, this.flag, ModelBakery.BANNER_BASE, true, list);
        matrixStack.popPose();
    }

    public static void renderPatterns(PoseStack matrixStack, MultiBufferSource bufferSource, int light, int overlay, ModelPart part, Material material, boolean b, List<Pair<BannerPattern, DyeColor>> patterns) {
        renderPatterns(matrixStack, bufferSource, light, overlay, part, material, b, patterns, false);
    }

    public static void renderPatterns(PoseStack matrixStack, MultiBufferSource bufferSource, int light, int overlay, ModelPart part, Material material, boolean b, List<Pair<BannerPattern, DyeColor>> patterns, boolean b2) {
        part.render(matrixStack, material.buffer(bufferSource, RenderType::entitySolid, b2), light, overlay);
        for(int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<BannerPattern, DyeColor> pair = patterns.get(i);
            float[] afloat = pair.getSecond().getTextureDiffuseColors();
            BannerPattern bannerpattern = pair.getFirst();
            Material mat = b ? Sheets.getBannerMaterial(bannerpattern) : Sheets.getShieldMaterial(bannerpattern);
            part.render(matrixStack, mat.buffer(bufferSource, RenderType::entityNoOutline), light, overlay, afloat[0], afloat[1], afloat[2], 1.0F);
        }

    }
}