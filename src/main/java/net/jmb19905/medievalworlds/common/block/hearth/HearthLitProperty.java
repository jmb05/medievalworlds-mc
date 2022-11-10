package net.jmb19905.medievalworlds.common.block.hearth;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum HearthLitProperty implements StringRepresentable {

    OFF("off"),
    SIMMERING("simmering"),
    ON("on");

    private final String name;

    HearthLitProperty(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
