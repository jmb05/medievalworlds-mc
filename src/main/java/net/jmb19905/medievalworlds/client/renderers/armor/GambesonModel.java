package net.jmb19905.medievalworlds.client.renderers.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GambesonModel extends CustomArmorModel {

    public GambesonModel(ModelPart root) {
        super(root);
        addToRender(root.getChild("head"));
        addToRender(root.getChild("left_arm"));
        addToRender(root.getChild("body"));
        addToRender(root.getChild("right_arm"));
    }

    public static LayerDefinition createCoifLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        CubeDeformation deformation = new CubeDeformation(0.6f);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createGambesonLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        CubeDeformation deformation = new CubeDeformation(0.6f);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 12).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 16.0F, 4.0F, deformation), PartPose.offset(0.0F, 12.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(-5.0F, 14, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(5.0F, 14, 0.0F));

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

}