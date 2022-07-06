package net.jmb19905.medievalworlds.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.SimpleBloomeryBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.jetbrains.annotations.NotNull;

public class BloomeryRenderer implements BlockEntityRenderer<SimpleBloomeryBlockEntity> {

    private final ItemRenderer itemRenderer;

    public BloomeryRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(@NotNull SimpleBloomeryBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        //this.itemRenderer.renderStatic(entity.getInventory().getStackInSlot(1), ItemTransforms.TransformType.FIXED, light, overlay, poseStack, bufferSource, entity.hashCode());
    }
}
