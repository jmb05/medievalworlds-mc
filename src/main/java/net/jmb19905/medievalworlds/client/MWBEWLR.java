package net.jmb19905.medievalworlds.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jmb19905.medievalworlds.client.renderers.item.LongbowModel;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MWBEWLR extends BlockEntityWithoutLevelRenderer {

    private static final Minecraft minecraft = Minecraft.getInstance();

    private LongbowModel longbowModel;

    public MWBEWLR() {
        super(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        longbowModel = new LongbowModel(minecraft.getEntityModels().bakeLayer(ClientSetup.LONGBOW_LAYER));
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, ItemTransforms.@NotNull TransformType transformType, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        var item = stack.getItem();
        if (item == MWItems.LONGBOW.get()) {
            if (longbowModel == null) longbowModel = new LongbowModel(minecraft.getEntityModels().bakeLayer(ClientSetup.LONGBOW_LAYER));
            poseStack.pushPose();
            poseStack.translate(.5, 0.6, .6);
            if (minecraft.player != null) {
                LocalPlayer player = minecraft.player;
                if (player.getUseItem() == stack) {
                    int i = stack.getUseDuration() - player.getUseItemRemainingTicks();
                    longbowModel.setArrowVisible(true);
                    longbowModel.setPullback(BowItem.getPowerForTime(i));
                } else {
                    longbowModel.setArrowVisible(false);
                    longbowModel.setPullback(0);
                }
                /*if (minecraft.options.getCameraType().isFirstPerson()) {
                    poseStack.translate(0, -0.3, -0.2);
                    poseStack.mulPose(Vector3f.XN.rotationDegrees(20));
                }*/
            }
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, longbowModel.renderType(LongbowModel.TEXTURE), false, stack.hasFoil());
            longbowModel.renderToBuffer(poseStack, vertexconsumer, light, overlay, 1, 1, 1, 1);
            poseStack.popPose();
        }
    }
}
