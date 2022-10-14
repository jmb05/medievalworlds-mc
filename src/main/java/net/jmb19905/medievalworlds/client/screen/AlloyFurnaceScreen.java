package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.AlloyFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/alloy_furnace.png");
    private final AlloyFurnaceMenu screenMenu;

    public AlloyFurnaceScreen(AlloyFurnaceMenu screenMenu, Inventory inv, Component titleIn) {
        super(screenMenu, inv, titleIn);
        this.screenMenu = screenMenu;

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void init(){
        super.init();
        boolean widthTooNarrow = this.width < 379;
        assert this.minecraft != null;
    }

    @Override
    protected void renderBg(@Nonnull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, this.leftPos + 79, this.topPos + 35, 176, 14, screenMenu.getSmeltProgressionScaled(), 16);
        int x = leftPos + 39;
        int y = topPos + 35 + screenMenu.getBurnProgressionScaled();
        int u = 176;
        int v = screenMenu.getBurnProgressionScaled();
        int w = 14;
        int h = screenMenu.getCurrentMaxBurnTime() != 0 ? 14 - screenMenu.getBurnProgressionScaled() : 0;
        this.blit(stack, x, y, u, v, w, h);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

}