package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomAnvilScreen extends AbstractContainerScreen<CustomAnvilMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/anvil.png");
    private final CustomAnvilMenu menu;
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public CustomAnvilScreen(CustomAnvilMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.menu = menu;
        this.menu.registerUpdateListener(this::containerChanged);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        this.renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int)(41.0F * this.scrollOffs);
        this.blit(stack, this.leftPos + 119, this.topPos + 15 + k, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int rightPos = this.leftPos + 52;
        int buttonStartFromTop = this.topPos + 14;
        int i = this.startIndex + 12;
        this.renderButtons(stack, mouseX, mouseY, rightPos, buttonStartFromTop, i);
        this.renderRecipes(rightPos, buttonStartFromTop, i);
    }

    protected void renderTooltip(@Nonnull PoseStack stack, int mouseX, int mouseY) {
        super.renderTooltip(stack, mouseX, mouseY);
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;
            List<AnvilRecipe> list = this.menu.getRecipes();

            for(int l = this.startIndex; l < k && l < this.menu.getNumRecipes(); ++l) {
                int i1 = l - this.startIndex;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if (mouseX >= j1 && mouseX < j1 + 16 && mouseY >= k1 && mouseY < k1 + 18) {
                    this.renderTooltip(stack, list.get(l).getResultItem(), mouseX, mouseY);
                }
            }
        }
    }

    private void renderButtons(PoseStack stack, int mouseX, int mouseY, int rightPos, int buttonStartFromTop, int t) {
        for(int i = this.startIndex; i < t && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = rightPos + j % 4 * 16;
            int l = j / 4;
            int i1 = buttonStartFromTop + l * 18 + 2;
            int j1 = this.imageHeight;
            if (i == this.menu.getSelectedRecipeIndex()) {
                j1 += 18;
            } else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18) {
                j1 += 36;
            }
            this.blit(stack, k, i1 - 1, 0, j1, 16, 18);
        }
    }

    private void renderRecipes(int rightPos, int buttonStartFromTop, int t) {
        List<AnvilRecipe> list = this.menu.getRecipes();

        for(int i = this.startIndex; i < t && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = rightPos + j % 4 * 16;
            int l = j / 4;
            int i1 = buttonStartFromTop + l * 18 + 2;
            assert this.minecraft != null;
            this.minecraft.getItemRenderer().renderAndDecorateItem(list.get(i).getResultItem(), k, i1);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;

            for(int l = this.startIndex; l < k; ++l) {
                int i1 = l - this.startIndex;
                double d0 = mouseX - (double)(i + i1 % 4 * 16);
                double d1 = mouseY - (double)(j + i1 / 4 * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D) {
                    assert this.minecraft != null;
                    assert this.minecraft.player != null;
                    if (this.menu.clickMenuButton(this.minecraft.player, l)) {
                        this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                        assert this.minecraft.gameMode != null;
                        try {
                            NetworkStartupCommon.simpleChannel.send(PacketDistributor.SERVER.noArg(), new AnvilRecipeSelectedPacket(this.menu.containerId, menu.getRecipes().get(l).getId()));
                            NetworkStartupCommon.simpleChannel.send(PacketDistributor.SERVER.noArg(), new AnvilRecipeSelectedPacket(this.menu.containerId, menu.getRecipes().get(l).getId()));
                        } catch (IndexOutOfBoundsException ignored) {}
                        return true;
                    }
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean mouseDragged(double p_99322_, double p_99323_, int p_99324_, double p_99325_, double p_99326_) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)p_99323_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(p_99322_, p_99323_, p_99324_, p_99325_, p_99326_);
        }
    }

    public boolean mouseScrolled(double p_99314_, double p_99315_, double p_99316_) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            this.scrollOffs = (float)((double)this.scrollOffs - p_99316_ / (double)i);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}
