package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.config.ClientConfig;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.item.IAnvilItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;

public class CustomAnvilRenderer implements BlockEntityRenderer<AnvilBlockEntity> {

    private final ModelPart root;
    private final ItemRenderer itemRenderer;

    public CustomAnvilRenderer(BlockEntityRendererProvider.Context context){
        root = context.bakeLayer(ClientSetup.CUSTOM_ANVIL_LAYER);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("ingot", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, 0.0F, -4, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 16.0F, 8.0F));

        PartDefinition pickaxe_head = partDefinition.addOrReplaceChild("pickaxe_head", CubeListBuilder.create(), PartPose.offset(8,18,8));
        pickaxe_head.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 4).addBox(-2.0F, -2.001F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        pickaxe_head.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6173F, 0.0F, -0.0761F, 0.0F, -0.3927F, 0.0F));
        pickaxe_head.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 8).addBox(-5.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6173F, 0.0F, -0.0761F, 0.0F, 0.3927F, 0.0F));

        PartDefinition shovel_head = partDefinition.addOrReplaceChild("shovel_head", CubeListBuilder.create(), PartPose.offset(8,17,8));
        shovel_head.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-1.5F, -1.003F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        shovel_head.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.001F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -4.0F, 0.0F, -2.138F, 0.0F));
        shovel_head.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.005F, 0.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1868F, 0.0F, -2.9254F, 0.0F, 0.1309F, 0.0F));
        shovel_head.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.006F, 0.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1868F, 0.0F, -2.9254F, 0.0F, -0.1309F, 0.0F));
        shovel_head.addOrReplaceChild("5", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.002F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -4.0F, 0.0F, -1.0036F, 0.0F));

        PartDefinition hoe_head = partDefinition.addOrReplaceChild("hoe_head", CubeListBuilder.create(), PartPose.offset(8, 18, 8));
        hoe_head.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.001F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        hoe_head.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 4).addBox(-7.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6173F, 0.0F, -0.0761F, 0.0F, 0.3927F, 0.0F));

        PartDefinition axe_head = partDefinition.addOrReplaceChild("axe_head", CubeListBuilder.create(), PartPose.offset(8, 17, 8));
        axe_head.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 6).addBox(-5.0F, -1.001F, -2.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        axe_head.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -5.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, -2.0F, -3.1416F, -0.7854F, 3.1416F));
        axe_head.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, -5.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition sword_blade = partDefinition.addOrReplaceChild("sword_blade", CubeListBuilder.create(), PartPose.offset(8, 17, 8));
        sword_blade.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 2).addBox(-7.5F, -1.0F, -1.5F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-6.7716F, -0.997F, 0.6481F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-6.7716F, -0.998F, -1.6481F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        sword_blade.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.998F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -0.5F, -3.1416F, 0.3927F, 3.1416F));
        sword_blade.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.997F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.5F, -3.1416F, -0.3927F, 3.1416F));
        sword_blade.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -0.999F, -7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        partDefinition.addOrReplaceChild("armor_plate", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 17, 8));

        PartDefinition longsword_blade = partDefinition.addOrReplaceChild("longsword_blade", CubeListBuilder.create(), PartPose.offset(8, 17, 8));
        longsword_blade.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -1.0F, -1.5F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(0.0F, -1.0F, -1.5F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-17.5F, -0.999F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(0.3255F, -1.001F, -2.1483F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(13.0F, -1.0F, -1.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(0.3692F, -1.001F, 1.1473F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 8).addBox(17.0F, -1.0F, -2.1483F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 8).addBox(17.0F, -1.0F, 1.1483F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 8).addBox(13.3692F, -1.001F, 1.1473F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 8).addBox(13.3255F, -1.001F, -2.1483F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));
        longsword_blade.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 10).addBox(-0.0436F, -1.002F, -1.0009F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.5749F, 0.0F, 1.5831F, 0.0F, -0.0436F, 0.0F));
        longsword_blade.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 10).addBox(-0.0436F, -1.002F, -0.0009F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.6185F, 0.0F, -1.5822F, 0.0F, 0.0436F, 0.0F));
        longsword_blade.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.001F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.5F, 0.0F, 0.5F, 0.0F, -0.2182F, 0.0F));
        longsword_blade.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.001F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.5F, 0.0F, -0.5F, 0.0F, 0.2182F, 0.0F));

        partDefinition.addOrReplaceChild("hammer_head_raw",
                CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-3.5F, -7.0F, -7.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-3.5F, -7.0F, 0.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8, 23, 8));

        PartDefinition battleaxe_head = partDefinition.addOrReplaceChild("battle_axe_head", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-3.0F, -1.01F, -8.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-3.0F, -1.01F, 0.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 17, 8));
        battleaxe_head.addOrReplaceChild("0", CubeListBuilder.create().texOffs(0, 5).addBox(-7.0F, -1.0F, -4.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 8.0F, 0.0F, -1.0472F, 0.0F));
        battleaxe_head.addOrReplaceChild("1", CubeListBuilder.create().texOffs(0, 5).addBox(-7.0F, -1.0F, 0.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 8.0F, 0.0F, -2.0944F, 0.0F));
        battleaxe_head.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -4.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, -8.0F, 0.0F, 2.0944F, 0.0F));
        battleaxe_head.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, 0.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, -8.0F, 0.0F, 1.0472F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void render(@Nonnull AnvilBlockEntity entity, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack item = entity.getItemToShow();
        if(ClientConfig.FANCY_ANVIL_RENDERING.get() && item.getItem() instanceof IAnvilItem) {
            try {
                ModelPart part = root.getChild(((IAnvilItem) item.getItem()).getType());
                part.yRot = (float) Math.toRadians(285 - (entity.getFacingDirection().get2DDataValue() * 90));
                part.render(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(((IAnvilItem) item.getItem()).getAnvilTexture())), light, overlay);
            }catch (NoSuchElementException e) {
                renderAlternate(entity, item, stack, bufferSource, light, overlay);
            }
        }else {
            renderAlternate(entity, item, stack, bufferSource, light, overlay);
        }
    }

    public void renderAlternate(AnvilBlockEntity entity, ItemStack item, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
        stack.pushPose();
        stack.translate(0.5, 1, 0.5);
        stack.mulPose(Vector3f.XP.rotationDegrees(90));
        stack.scale(.5f, .5f, .5f);
        this.itemRenderer.renderStatic(item, ItemTransforms.TransformType.FIXED, light, overlay, stack, bufferSource, entity.hashCode());
        stack.popPose();
    }

}
