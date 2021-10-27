package net.jmb19905.medievalworlds.common;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraftforge.fml.common.Mod;

public class MWEvents {

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {



    }

}
