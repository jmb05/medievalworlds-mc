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
 * Adapted from Mekanism:
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2017-2022 Aidan C. Brady
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

    MWKeyBinding(String description, IKeyConflictContext keyConflictContext, KeyModifier modifier, InputConstants.Key key, String category, @org.jetbrains.annotations.Nullable BiConsumer<KeyMapping, Boolean> onKeyDown, @org.jetbrains.annotations.Nullable Consumer<KeyMapping> onKeyUp, @org.jetbrains.annotations.Nullable BooleanSupplier toggleable, boolean repeating) {
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
        return super.isDown() && (isConflictContextAndModifierActive() || isToggleable());
    }
}
