package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.BloomeryMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class BloomeryScreen extends AbstractContainerScreen<BloomeryMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/bloomery.png");
    private final BloomeryMenu screenContainer;

    public BloomeryScreen(BloomeryMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.screenContainer = screenContainer;

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@Nonnull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, this.leftPos + 79, this.topPos + 35, 176, 14, screenContainer.getSmeltProgressionScaled(), 16);
        this.blit(stack, this.leftPos + 39, this.topPos + 35 + screenContainer.getBurnProgressionScaled(), 176, screenContainer.getBurnProgressionScaled(), 14, screenContainer.getCurrentMaxBurnTime() != 0 ? 14 - screenContainer.getBurnProgressionScaled() : 0);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

}
