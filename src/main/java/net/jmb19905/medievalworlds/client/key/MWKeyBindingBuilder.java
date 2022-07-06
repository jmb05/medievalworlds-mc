package net.jmb19905.medievalworlds.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class MWKeyBindingBuilder {

    private String description;
    private IKeyConflictContext keyConflictContext = KeyConflictContext.UNIVERSAL;
    private KeyModifier modifier = KeyModifier.NONE;
    private InputConstants.Key key;
    private String category = "medievalworlds";
    private BiConsumer<KeyMapping, Boolean> onKeyDown;
    private Consumer<KeyMapping> onKeyUp;
    private BooleanSupplier toggleable;
    private boolean repeating;

    public MWKeyBindingBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MWKeyBindingBuilder conflictInGame() {
        this.keyConflictContext = KeyConflictContext.IN_GAME;
        return this;
    }

    public MWKeyBindingBuilder conflictInGui() {
        this.keyConflictContext = KeyConflictContext.GUI;
        return this;
    }

    public MWKeyBindingBuilder modifier(KeyModifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public MWKeyBindingBuilder keyCode(int keyCode) {
        return keyCode(InputConstants.Type.KEYSYM, keyCode);
    }

    public MWKeyBindingBuilder keyCode(InputConstants.Type keyType, int keyCode) {
        return keyCode(keyType.getOrCreate(keyCode));
    }

    public MWKeyBindingBuilder keyCode(InputConstants.Key key) {
        this.key = key;
        return this;
    }

    public MWKeyBindingBuilder category(String category) {
        this.category = category;
        return this;
    }

    public MWKeyBindingBuilder onKeyDown(BiConsumer<KeyMapping, Boolean> onKeyDown) {
        this.onKeyDown = onKeyDown;
        return this;
    }

    public MWKeyBindingBuilder onKeyUp(Consumer<KeyMapping> onKeyUp) {
        this.onKeyUp = onKeyUp;
        return this;
    }

    public MWKeyBindingBuilder toggleable() {
        return toggleable(() -> true);
    }

    public MWKeyBindingBuilder toggleable(BooleanSupplier booleanSupplier) {
        this.toggleable = booleanSupplier;
        return this;
    }

    public MWKeyBindingBuilder repeating() {
        this.repeating = true;
        return this;
    }

    public KeyMapping build() {
        return new MWKeyBinding(
                description,
                keyConflictContext,
                modifier,
                key,
                category,
                onKeyDown,
                onKeyUp,
                toggleable,
                repeating
        );
    }

}
