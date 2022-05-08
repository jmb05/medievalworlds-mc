package net.jmb19905.medievalworlds.client.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        CLIENT_SPEC = configBuilder.build();
    }

    public static ForgeConfigSpec.BooleanValue FANCY_ANVIL_RENDERING;

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        FANCY_ANVIL_RENDERING = builder.define("fancy_anvil_rendering", true);
    }

}
