package net.jmb19905.medievalworlds.client.renderers.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KnightArmorHelmetModel extends CustomArmorModel{

    public KnightArmorHelmetModel(ModelPart root) {
        super(root);
        addToRender(root.getChild("head"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeDeformation deformation = new CubeDeformation(.5f);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0,0).addBox(-4, -8, -4, 8, 8, 8, deformation)
                .texOffs(0, 16).addBox(-4.5F, -6.0F, -4.5F, 9.0F, 5.0F, 9.0F, deformation)
                .texOffs(6, 26).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F, deformation), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 26.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 26.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 36.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 36.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
