package net.jmb19905.medievalworlds.client.renderers.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import org.jetbrains.annotations.NotNull;

public class MWAnvilRenderer implements BlockEntityRenderer<AnvilBlockEntity> {
    private final ItemRenderer itemRenderer;

    public MWAnvilRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(@NotNull AnvilBlockEntity blockEntity, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack item = blockEntity.getItemToShow();
        stack.pushPose();
        stack.translate(0.5, 1, 0.5);
        Direction facing = blockEntity.getBlockState().getValue(AnvilBlock.FACING);
        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            stack.mulPose(Vector3f.YP.rotationDegrees( (facing.get2DDataValue() + 1) * 90));
        } else {
            stack.mulPose(Vector3f.YP.rotationDegrees( (facing.get2DDataValue() - 1) * 90));
        }
        stack.mulPose(Vector3f.XP.rotationDegrees(90));
        stack.scale(.5f, .5f, .5f);
        this.itemRenderer.renderStatic(item, ItemTransforms.TransformType.FIXED, light, overlay, stack, bufferSource, blockEntity.hashCode());
        stack.popPose();
    }
}
