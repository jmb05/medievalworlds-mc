package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CustomShield extends ShieldItem {

    public CustomShield(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return ClientSetup.getBEWLR();
            }
        });
    }
}
