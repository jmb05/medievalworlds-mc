package net.jmb19905.medievalworlds.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Mostly copied from Mekanism
 */
public class MWKeyBinding extends KeyMapping {

    @Nullable
    private final BiConsumer<KeyMapping, Boolean> onKeyDown;
    @Nullable
    private final Consumer<KeyMapping> onKeyUp;
    @Nullable
    private final BooleanSupplier toggleable;
    private final boolean repeating;
    private boolean lastState;

    MWKeyBinding(String description, IKeyConflictContext keyConflictContext, KeyModifier modifier, InputConstants.Key key, String category, BiConsumer<KeyMapping, Boolean> onKeyDown, Consumer<KeyMapping> onKeyUp, BooleanSupplier toggleable, boolean repeating) {
        super(description, keyConflictContext, modifier, key, category);
        this.onKeyDown = onKeyDown;
        this.onKeyUp = onKeyUp;
        this.toggleable = toggleable;
        this.repeating = repeating;
    }

    public boolean isToggleable() {
        return toggleable != null && toggleable.getAsBoolean();
    }

    @Override
    public void setDown(boolean value) {
        if(isToggleable()) {
            if(value && isConflictContextAndModifierActive()) {
                super.setDown(!this.isDown());
            }
        } else {
            super.setDown(value);
        }

        boolean state = isDown();
        if(state != lastState || (state && repeating)) {
            if(state) {
                if(onKeyDown != null) {
                    onKeyDown.accept(this, lastState);
                }
            } else if(onKeyUp != null) {
                onKeyUp.accept(this);
            }
            lastState = state;
        }
    }

    @Override
    public boolean isDown() {
        return isDown && (isConflictContextAndModifierActive() || isToggleable());
    }
}