package net.jmb19905.medievalworlds.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.model.RoundShieldModel;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MWBEWLR extends BlockEntityWithoutLevelRenderer {

    private static final Minecraft minecraft = Minecraft.getInstance();

    private RoundShieldModel roundShieldModel;
    public static final ResourceLocation ROUND_SHIELD_TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/round_shield.png");

    private final BellowsBlockEntity bellowsBlockEntity = new BellowsBlockEntity(BlockPos.ZERO, MWBlocks.BELLOWS.get().defaultBlockState());

    public MWBEWLR() {
        super(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.@NotNull TransformType transformType, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
        Item item = stack.getItem();
        if(item == MWItems.ROUND_SHIELD.get()) {
            if(roundShieldModel == null) roundShieldModel = new RoundShieldModel(minecraft.getEntityModels().bakeLayer(ClientSetup.ROUND_SHIELD_LAYER));
            poseStack.pushPose();
            poseStack.translate(.2, -1, .05);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            roundShieldModel.renderToBuffer(poseStack, bufferSource.getBuffer(roundShieldModel.renderType(ROUND_SHIELD_TEXTURE)), light, overlay, 1,1,1,1);
            poseStack.popPose();
        }else if(item == MWBlocks.BELLOWS_ITEM.get()) {
            minecraft.getBlockEntityRenderDispatcher().renderItem(bellowsBlockEntity, poseStack, bufferSource, light, overlay);
        }
    }
}
