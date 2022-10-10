package net.jmb19905.medievalworlds.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class MWBEWLR extends BlockEntityWithoutLevelRenderer {

    private static final Minecraft minecraft = Minecraft.getInstance();

    public MWBEWLR() {
        super(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
    }

}
