package net.jmb19905.medievalworlds.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

import javax.annotation.Nonnull;

public class CrownHelmetModel extends ModelArmor{
    public final ModelRenderer Helmet;
    public final ModelRenderer Overlay;

    public CrownHelmetModel() {
        super(EquipmentSlotType.HEAD);
        textureWidth = 32;
        textureHeight = 32;

        Overlay = new ModelRenderer(this);

        Helmet = new ModelRenderer(this);
        Helmet.setRotationPoint(0.0F, 24.0F, 0.0F);
        Helmet.setTextureOffset(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Helmet.render(matrixStack, buffer, packedLight, packedOverlay);
    }

}
