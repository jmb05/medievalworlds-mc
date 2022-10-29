package net.jmb19905.medievalworlds.client.renderers.entity;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.entity.MWArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MWArrowRenderer extends ArrowRenderer<MWArrow> {

    public MWArrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MWArrow arrow) {
        return new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/projectiles/" + arrow.getMaterial() + "_arrow.png");
    }
}
