package net.jmb19905.medievalworlds.common.block;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BloomeryActivationProperty implements StringRepresentable {
    OFF("off"),
    LIT("lit"),
    ACTIVATED("active");

    private final String name;

    BloomeryActivationProperty(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getSerializedName() {
        return name;
    }
}