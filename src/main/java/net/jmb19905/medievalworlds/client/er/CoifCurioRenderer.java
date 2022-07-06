package net.jmb19905.medievalworlds.client.er;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CoifCurioRenderer implements ICurioRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/models/armor/gambeson.png");

    private final GambesonModel modelSmall;
    private final GambesonModel modelFull;

    public CoifCurioRenderer() {
        this.modelSmall = new GambesonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.COIF_CURIO_LAYER));
        this.modelFull = new GambesonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.COIF_LAYER));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        GambesonModel model;
        if(slotContext.entity().getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            model = modelFull;
        } else {
            model = modelSmall;
        }

        matrixStack.pushPose();
        //matrixStack.scale(0.8f, 0.8f, 0.8f);
        if(renderLayerParent.getModel() instanceof HumanoidModel<?> playerModel) {
            model.head.copyFrom(playerModel.head);
        }
        model.head.render(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, 655360);
        matrixStack.popPose();
    }
}
