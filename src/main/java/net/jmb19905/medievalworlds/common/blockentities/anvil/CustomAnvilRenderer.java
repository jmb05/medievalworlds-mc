package net.jmb19905.medievalworlds.common.blockentities.anvil;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class CustomAnvilRenderer implements BlockEntityRenderer<CustomAnvilBlockEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil_ingot.png");
    private final ModelPart ingot;

    public CustomAnvilRenderer(BlockEntityRendererProvider.Context context){
        ModelPart root = context.bakeLayer(ClientSetup.CUSTOM_ANVIL_LAYER);
        this.ingot = root.getChild("ingot");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("ingot", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, 0.0F, -4, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 16.0F, 8.0F));

        return LayerDefinition.create(meshDefinition, 32, 16);
    }

    @Override
    public void render(@Nonnull CustomAnvilBlockEntity entity, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferSource, int light, int overlay) {
        if(entity.getInventory().getStackInSlot(0).getItem() == Items.IRON_INGOT) {
            if(entity.getFacingDirection() == Direction.NORTH || entity.getFacingDirection() == Direction.SOUTH) {
                this.ingot.yRot = (float) Math.toRadians(15);
                //System.out.println("Rotated");
            }else {
                this.ingot.yRot = (float) Math.toRadians(105);
            }
            ingot.render(stack, bufferSource.getBuffer(RenderType.entitySolid(TEXTURE)), light, overlay);
        }
    }
}
