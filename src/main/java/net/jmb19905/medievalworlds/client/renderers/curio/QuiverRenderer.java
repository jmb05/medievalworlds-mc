package net.jmb19905.medievalworlds.client.renderers.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class QuiverRenderer implements ICurioRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/quiver.png");

    private final ModelPart quiver;
    private final ModelPart arrows;

    public QuiverRenderer() {
        ModelPart modelPart = Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.QUIVER_LAYER);
        this.quiver = modelPart.getChild("quiver");
        this.arrows = this.quiver.getChild("arrows");
    }

    public static LayerDefinition createQuiverLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition quiver = partdefinition.addOrReplaceChild("quiver", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        quiver.addOrReplaceChild("1", CubeListBuilder.create()
                .texOffs(12, 0).addBox(-0.5F, -7.0F, -4.5F, 1.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition arrows = quiver.addOrReplaceChild("arrows", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        arrows.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7F, -5.0F, 2.1F, 0.0F, -2.3562F, 0.0F));
        arrows.addOrReplaceChild("3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7F, -5.0F, 2.1F, 0.0F, -0.7854F, 0.0F));
        arrows.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.5F, 0.0F, 2.4871F, 0.0F));
        arrows.addOrReplaceChild("5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.5F, 0.0F, 0.9163F, 0.0F));
        arrows.addOrReplaceChild("6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -5.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        arrows.addOrReplaceChild("7", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -5.0F, 2.5F, 0.0F, 0.6981F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();
        QuiverInv inv = MWCapabilityManager.retrieveCapability(stack, QuiverInv.QUIVER_INV_CAPABILITY);
        arrows.visible = inv == null || inv.getFillLevel() > 0;
        if (slotContext.entity() instanceof Player player && player.isCrouching()) {
            matrixStack.translate(0, 0.2, 0);
            matrixStack.mulPose(Vector3f.XP.rotation(0.5f));
        }
        quiver.render(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, 655360);
        matrixStack.popPose();
    }
}
