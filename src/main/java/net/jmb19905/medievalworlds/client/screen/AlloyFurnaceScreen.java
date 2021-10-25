package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.AlloyFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/alloy_furnace.png");
    private final AlloyFurnaceMenu screenContainer;

    private final int left;
    private final int top;
    private final int width_;
    private final int height_;

    public AlloyFurnaceScreen(AlloyFurnaceMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.screenContainer = screenContainer;

        this.leftPos = 0;
        this.topPos = 0;
        this.width = 176;
        this.height = 166;

        this.left = leftPos;
        this.top = topPos;
        this.width_ = width;
        this.height_ = height;
    }

    @Override
    protected void renderBg(@Nonnull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, this.left, this.top, 0, 0, this.width_, this.height_);
        this.blit(stack, this.left + 79, this.top + 36, 176, 14, screenContainer.getSmeltProgressionScaled(), 16);
        this.blit(stack, this.left + 39, this.top + 36 + screenContainer.getBurnProgressionScaled(), 176, screenContainer.getBurnProgressionScaled(),14, screenContainer.getCurrentMaxBurnTime() != 0 ? 14 - screenContainer.getBurnProgressionScaled() : 0);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack stack, int mouseX, int mouseY) {
        super.renderLabels(stack, mouseX, mouseY);
        this.font.draw(stack, this.title.getString(), 8,5, 0x404040);
        this.font.draw(stack, this.playerInventoryTitle.getString(), 8, 72, 0x404040);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(stack);
        super.render(stack, p_render_1_, p_render_2_, p_render_3_);
        this.renderTooltip(stack, p_render_1_, p_render_2_);
    }
}
