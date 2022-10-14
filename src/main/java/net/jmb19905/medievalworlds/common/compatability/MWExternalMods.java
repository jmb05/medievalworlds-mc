package net.jmb19905.medievalworlds.common.compatability;

import net.minecraftforge.fml.ModList;

public enum MWExternalMods {

    CURIOS("curios"),
    JEI("jei");

    private final boolean loaded;

    MWExternalMods(String modid) {
        this.loaded = ModList.get() != null && ModList.get().isLoaded(modid);
    }

    public boolean isLoaded() {
        return loaded;
    }
}
