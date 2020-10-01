package net.jmb19905.medievalworlds.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.containers.AlloyFurnaceContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlloyFurnaceScreen extends ContainerScreen<AlloyFurnaceContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/alloy_furnace.png");
    private final AlloyFurnaceContainer screenContainer;

    public AlloyFurnaceScreen(AlloyFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.screenContainer = screenContainer;

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1, 1, 1, 1);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.blit(this.guiLeft + 79, this.guiTop + 35, 176, 14, screenContainer.getSmeltProgressionScaled(), 16);
        this.blit(this.guiLeft + 39, this.guiTop + 35 + screenContainer.getBurnProgressionScaled(), 176, screenContainer.getBurnProgressionScaled(), 14, screenContainer.getCurrentMaxBurnTime() != 0 ? 14 - screenContainer.getBurnProgressionScaled() : 0);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8,5, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, 72, 0x404040);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }
}
