package net.jmb19905.medievalworlds.core.compatability;

import net.minecraftforge.fml.ModList;

public enum ExternalMods {

    CURIOS("curios");

    private final boolean loaded;

    ExternalMods(String modid){
        this.loaded = ModList.get() != null && ModList.get().isLoaded(modid);
    }

    public boolean isLoaded(){
        return this.loaded;
    }

}
