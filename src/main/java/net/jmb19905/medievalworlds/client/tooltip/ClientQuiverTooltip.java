package net.jmb19905.medievalworlds.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.quiver.QuiverTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientQuiverTooltip implements ClientTooltipComponent {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/quiver.png");

    private static final int MARGIN_Y = 4;
    private static final int TEX_SIZE = 128;
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 18;

    private final List<ItemStack> items;

    public ClientQuiverTooltip(QuiverTooltip quiverTooltip) {
        this.items = quiverTooltip.getItems();
        //this.items = Lists.reverse(quiverTooltip.getItems());
    }

    @Override
    public int getHeight() {
        return SLOT_SIZE_Y + 2 + MARGIN_Y;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return this.gridSizeX() * SLOT_SIZE_X + 2;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull PoseStack poseStack, @NotNull ItemRenderer itemRenderer, int blitOffset) {
        int index = 0;
        for(int slotIndex = 0; slotIndex < this.gridSizeX(); ++slotIndex) {
            int slotX = x + slotIndex * SLOT_SIZE_X + 1;
            int slotY = y + 1;
            this.renderSlot(slotX, slotY, index++, font, poseStack, itemRenderer, blitOffset);
        }
    }

    private void renderSlot(int x, int y, int index, Font font, PoseStack poseStack, ItemRenderer itemRenderer, int blitOffset) {
        if (index < this.items.size()) {
            ItemStack itemstack = this.items.get(index);
            this.blit(poseStack, x, y, blitOffset);
            itemRenderer.renderAndDecorateItem(itemstack, x + 1, y + 1, index);
            itemRenderer.renderGuiItemDecorations(font, itemstack, x + 1, y + 1);
            if (index == 0) {
                AbstractContainerScreen.renderSlotHighlight(poseStack, x + 1, y + 1, blitOffset);
            }
        }
    }

    private void blit(PoseStack poseStack, int x, int y, int blitOffset) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(poseStack, x, y, blitOffset, 0, 0, SLOT_SIZE_X, SLOT_SIZE_Y, TEX_SIZE, TEX_SIZE);
    }

    private int gridSizeX() {
        long itemCount = items.stream()
                .filter(stack -> !stack.isEmpty())
                .count();
        return (int) (Math.min(itemCount + 1, items.size()));
    }
}