package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.ForgeBaseMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class ForgeScreen extends AbstractContainerScreen<ForgeBaseMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/forge.png");
    private final ForgeBaseMenu menu;

    public ForgeScreen(ForgeBaseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.menu = menu;

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@Nonnull PoseStack stack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        int x = leftPos + 80;
        int y = topPos + 26 + menu.getBurnProgressionScaled();
        int u = 176;
        int v = menu.getBurnProgressionScaled();
        int w = 14;
        int h = menu.getCurrentMaxBurnTime() != 0 ? 14 - menu.getBurnProgressionScaled() : 0;
        this.blit(stack, x, y, u, v, w, h);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }
}
