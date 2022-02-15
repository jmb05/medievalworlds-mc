package net.jmb19905.medievalworlds.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomSmithingScreen extends ItemCombinerScreen<CustomSmithingMenu> {
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/smithing.png");
    private static final Component TOO_EXPENSIVE_TEXT = new TranslatableComponent("container.repair.expensive");
    private EditBox name;
    private final Player player;

    public CustomSmithingScreen(CustomSmithingMenu menu, Inventory inv, Component component) {
        super(menu, inv, component, SMITHING_LOCATION);
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
    protected void subInit() {
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new EditBox(this.font, i + 62, j + 24, 103, 12, TextComponent.EMPTY);
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
    }

    public void resize(@NotNull Minecraft minecraft, int x, int y) {
        String s = this.name.getValue();
        this.init(minecraft, x, y);
        this.name.setValue(s);
    }

    public void removed() {
        super.removed();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

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
            this.minecraft.player.connection.send(new ServerboundRenameItemPacket(s));
        }
    }

    protected void renderLabels(@NotNull PoseStack stack, int x, int y) {
        RenderSystem.disableBlend();
        super.renderLabels(stack, x, y);
        int i = this.menu.getCost();
        if (i > 0) {
            int j = 8453920;
            Component component;
            if (i >= 40 && !Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player).getAbilities().instabuild) {
                component = TOO_EXPENSIVE_TEXT;
                j = 16736352;
            } else if (!this.menu.getSlot(2).hasItem()) {
                component = null;
            } else {
                component = new TranslatableComponent("container.repair.cost", i);
                if (!this.menu.getSlot(2).mayPickup(this.player)) {
                    j = 16736352;
                }
            }

            if (component != null) {
                int k = this.imageWidth - 8 - this.font.width(component) - 2;
                fill(stack, k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                this.font.drawShadow(stack, component, (float)k, 69.0F, j);
            }
        }
    }

    public void renderFg(@NotNull PoseStack stack, int p_97895_, int p_97896_, float p_97897_) {
        this.name.render(stack, p_97895_, p_97896_, p_97897_);
    }

    public void slotChanged(@NotNull AbstractContainerMenu menu, int p_97883_, @NotNull ItemStack itemStack) {
        if (p_97883_ == 0) {
            this.name.setValue(itemStack.isEmpty() ? "" : itemStack.getHoverName().getString());
            this.name.setEditable(!itemStack.isEmpty());
            this.setFocused(this.name);
        }

    }

}
