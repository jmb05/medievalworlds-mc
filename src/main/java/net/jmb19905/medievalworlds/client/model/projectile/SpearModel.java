package net.jmb19905.medievalworlds.client.model.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

public class SpearModel extends Model {
	private final ModelRenderer spear;

	public SpearModel() {
		super(RenderType::getEntitySolid);
		textureWidth = 16;
		textureHeight = 16;

		spear = new ModelRenderer(this);
		spear.setRotationPoint(0.0F, 24.0F, 0.0F);
		spear.setTextureOffset(0, 0).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F, false);
		spear.setTextureOffset(8, 8).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		spear.setTextureOffset(0, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		spear.setTextureOffset(8, 5).addBox(-1.0F, -20.0F, 1.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(10, 0).addBox(1.0F, -20.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
		spear.setTextureOffset(8, 0).addBox(-1.0F, -20.0F, -2.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(11, 12).addBox(-0.5F, -25.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(10, 6).addBox(-2.0F, -20.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
		spear.setTextureOffset(12, 14).addBox(-2.0F, -17.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(8, 14).addBox(1.0F, -17.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(8, 14).addBox(1.0F, -17.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(12, 14).addBox(-2.0F, -17.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		spear.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F, true);
		spear.setTextureOffset(0, 0).addBox(-1.0F, 14.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		spear.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}