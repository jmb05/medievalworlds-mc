package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.item.IAnvilItem;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class CustomAnvilRenderer implements BlockEntityRenderer<AnvilBlockEntity> {

    private final ModelPart root;

    public CustomAnvilRenderer(BlockEntityRendererProvider.Context context){
        root = context.bakeLayer(ClientSetup.CUSTOM_ANVIL_LAYER);
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("ingot", CubeListBuilder.create().texOffs(36, 13).addBox(-1.5f, 0.0F, -4, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 16.0F, 8.0F));
        partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 16.0F, 8.0F));
        partDefinition.addOrReplaceChild("medium_plate", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, 0, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 16, 8));
        partDefinition.addOrReplaceChild("large_plate", CubeListBuilder.create().texOffs(0,11).addBox(-6,0,-6, 12,1,12, new CubeDeformation(0)), PartPose.offset(8, 16, 8));

        partDefinition.addOrReplaceChild("large_bar", CubeListBuilder.create().texOffs(34, 28).addBox(3.5f, 0, -1.5F, 7, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 24).addBox(-3.5f, 0, -1.5F, 7, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 16, 8));

        partDefinition.addOrReplaceChild("medium_bar", CubeListBuilder.create().texOffs(16, 28).addBox(3, 0, -1.5F, 6, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 24).addBox(-3, 0, -1.5F, 6, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 16, 8));

        partDefinition.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 28).addBox(2.5f, 0, -1.5F, 5, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-2.5f, 0, -1.5F, 5, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 16, 8));

        partDefinition.addOrReplaceChild("bloom", CubeListBuilder.create().texOffs(0, 9).addBox(0, 1, 0, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(6, 15).addBox(-1.0F, 2.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(0.0F, 0.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(-1.0F, 1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(-1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 14).addBox(-1.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(-1.0F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(2.0F, 1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 15).addBox(0.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 14).addBox(1.0F, 3.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(3.0F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 14).addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 15).addBox(3.0F, 2.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(3.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(3.0F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(2.0F, 3.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 15).addBox(1.0F, 1.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 14).addBox(2.0F, 0.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 16).addBox(0.0F, 1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 14).addBox(0.0F, 0.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(2.0F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(0.0F, 3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 17).addBox(3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8, 16, 8));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void render(@Nonnull AnvilBlockEntity entity, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack anvilInputItem = entity.getInventory().getStackInSlot(0);
        ItemStack anvilResultItem = entity.getInventory().getStackInSlot(1);
        if(anvilInputItem.getItem() instanceof IAnvilItem || anvilResultItem.getItem() instanceof IAnvilItem) {
            ItemStack itemShown = entity.getItemToShow();
            ModelPart part = root.getChild(((IAnvilItem) itemShown.getItem()).getType());
            if(entity.getFacingDirection() == Direction.NORTH || entity.getFacingDirection() == Direction.SOUTH) {
                part.yRot = (float) Math.toRadians(15);
            }else {
                part.yRot = (float) Math.toRadians(105);
            }
            if(part == root.getChild("bar") || part == root.getChild("medium_bar") || part == root.getChild("large_bar")) {
                part.yRot += 45;
            }
            part.render(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(((IAnvilItem) itemShown.getItem()).getTexture())), light, overlay);
            if(part == root.getChild("bar") || part == root.getChild("medium_bar") || part == root.getChild("large_bar")) {
                part.yRot -= 45;
            }
        }
    }
}
