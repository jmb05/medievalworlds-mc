package net.jmb19905.medievalworlds.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public class CrownHelmetModel extends CustomArmorModel {
    private static final String HELMET = "helmet";
    public final ModelPart helmet;

    public CrownHelmetModel(ModelPart part) {
        super(part, EquipmentSlot.HEAD);
        this.helmet = part.getChild(HELMET);
        //CubeListBuilder.create().texOffs(0,0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 11.0F, 8.0F).getCubes();
        //Helmet = new ModelPart(c);
        //Helmet.setPos(0.0F, 24.0F, 0.0F);
        //Helmet.setTextureOffset(0, 0).addBox();
    }

}
