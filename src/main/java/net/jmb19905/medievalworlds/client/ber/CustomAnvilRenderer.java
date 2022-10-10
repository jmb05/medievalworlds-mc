package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomAnvilRenderer implements BlockEntityRenderer<AnvilBlockEntity> {

    private final ModelPart root;
    private final ItemRenderer itemRenderer;

    public CustomAnvilRenderer(BlockEntityRendererProvider.Context context) {
        root = context.bakeLayer(ClientSetup.CUSTOM_ANVIL_LAYER);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void render(@NotNull AnvilBlockEntity blockEntity, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack item = blockEntity.getItemToShow();
        stack.pushPose();
        stack.translate(0.5, 1, 0.5);
        stack.mulPose(Vector3f.XP.rotationDegrees(90));
        stack.scale(.5f, .5f, .5f);
        this.itemRenderer.renderStatic(item, ItemTransforms.TransformType.FIXED, light, overlay, stack, bufferSource, blockEntity.hashCode());
        stack.popPose();
    }
}
