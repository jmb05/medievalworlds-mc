package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.jmb19905.medievalworlds.common.networking.MWServerboundRenameItemPacket;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class CustomSmithingScreen extends AbstractContainerScreen<CustomSmithingMenu> implements ContainerListener {
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/smithing.png");
    private static final Component TOO_EXPENSIVE_TEXT = Component.translatable("container.repair.expensive");
    private EditBox name;
    private final Player player;

    private static final int GREEN = 0x80FF20;
    private static final int RED = 0xFF6060;

    public CustomSmithingScreen(CustomSmithingMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
        this.player = inv.player;
        this.titleLabelX = 60;
        this.titleLabelY = 10;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.name.tick();
    }

    @Override
    protected void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new EditBox(this.font, i + 62, j + 24, 103, 12, Component.empty());
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue("");
        this.addWidget(this.name);
        this.setInitialFocus(this.name);
        this.name.setEditable(false);
        this.menu.addSlotListener(this);
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int x, int y) {
        String s = this.name.getValue();
        this.init(minecraft, x, y);
        this.name.setValue(s);
    }

    @Override
    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
        System.out.println("Removed Slot Listener");
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void render(@NotNull PoseStack stack, int x, int y, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, x, y, partialTicks);
        RenderSystem.disableBlend();
        this.renderFg(stack, x, y, partialTicks);
        this.renderTooltip(stack, x, y);
    }

    @Override
    public boolean keyPressed(int key, int x, int y) {
        if (key == 256) {
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
        }

        return this.name.keyPressed(key, x, y) || this.name.canConsumeInput() || super.keyPressed(key, x, y);
    }

    private void onNameChanged(String name) {
        if (!name.isEmpty()) {
            String s = name;
            Slot slot = this.menu.getSlot(0);
            if (slot.hasItem() && !slot.getItem().hasCustomHoverName() && name.equals(slot.getItem().getHoverName().getString())) {
                s = "";
            }

            this.menu.setItemName(s);
            assert Objects.requireNonNull(this.minecraft).player != null;
            assert this.minecraft.player != null;
            NetworkStartupCommon.simpleChannel.send(PacketDistributor.SERVER.noArg(), new MWServerboundRenameItemPacket(s));
        }
    }

    @Override
    protected void renderLabels(@NotNull PoseStack stack, int x, int y) {
        RenderSystem.disableBlend();
        super.renderLabels(stack, x, y);
        int cost = this.menu.getCost();
        if (cost > 0) {
            int color = GREEN;
            Component component;
            if (cost >= 40 && !Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player).getAbilities().instabuild) {
                component = TOO_EXPENSIVE_TEXT;
                color = RED;
            } else if (!this.menu.getSlot(2).hasItem()) {
                component = null;
            } else {
                component = Component.translatable("container.repair.cost", cost);
                if (!this.menu.getSlot(2).mayPickup(this.player)) {
                    color = RED;
                }
            }

            if (component != null) {
                int k = this.imageWidth - 8 - this.font.width(component) - 2;
                fill(stack, k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                this.font.drawShadow(stack, component, (float)k, 69.0F, color);
            }
        }
    }

    public void renderFg(@NotNull PoseStack stack, int p_97895_, int p_97896_, float p_97897_) {
        this.name.render(stack, p_97895_, p_97896_, p_97897_);
    }

    @Override
    protected void renderBg(@NotNull PoseStack stack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SMITHING_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem()) {
            this.blit(stack, i + 99, j + 45, this.imageWidth, 0, 28, 21);
        }

    }

    @Override
    public void slotChanged(@NotNull AbstractContainerMenu menu, int slot, @NotNull ItemStack itemStack) {
        if(slot == 0) {
            this.name.setValue(itemStack.isEmpty() ? "" : itemStack.getHoverName().getString());
            this.name.setEditable(!itemStack.isEmpty());
            this.setFocused(this.name);
        }
    }

    @Override
    public void dataChanged(@NotNull AbstractContainerMenu menu, int x, int y) {}
}