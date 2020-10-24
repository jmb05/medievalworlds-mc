package net.jmb19905.medievalworlds.client.entity.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

import javax.annotation.Nonnull;

public class KnightArmorHelmetModel extends BipedModel<LivingEntity> {
	public final ModelRenderer Helmet;
	public final ModelRenderer Overlay;

	public KnightArmorHelmetModel() {
		super(1);
		textureWidth = 32;
		textureHeight = 32;

		Overlay = new ModelRenderer(this);

		Helmet = new ModelRenderer(this);
		Helmet.setRotationPoint(0.0F, 24.0F, 0.0F);
		Helmet.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		Helmet.setTextureOffset(0, 16).addBox(-4.5F, -6.0F, -4.5F, 9.0F, 5.0F, 9.0F, 0.0F, false);
		Helmet.setTextureOffset(6, 26).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(@Nonnull LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		if(!(entity instanceof ArmorStandEntity)) {
			super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			return;
		}

		ArmorStandEntity armorStandEntity = (ArmorStandEntity) entity;
		this.Helmet.rotateAngleX = ((float) Math.PI / 180F) * armorStandEntity.getHeadRotation().getX();
		this.Helmet.rotateAngleY = ((float) Math.PI / 180F) * armorStandEntity.getHeadRotation().getY();
		this.Helmet.rotateAngleZ = ((float) Math.PI / 180F) * armorStandEntity.getHeadRotation().getZ();
		this.Helmet.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.Overlay.copyModelAngles(this.Helmet);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Helmet.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}