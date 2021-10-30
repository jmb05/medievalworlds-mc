package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.menus.AlloyFurnaceMenu;
import net.jmb19905.medievalworlds.client.menus.BloomeryMenu;
import net.jmb19905.medievalworlds.client.menus.ForgeBaseMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE = MENU_TYPES.register("alloy_furnace", () -> IForgeContainerType.create(AlloyFurnaceMenu::new));
    public static final RegistryObject<MenuType<BloomeryMenu>> BLOOMERY = MENU_TYPES.register("bloomery", () -> IForgeContainerType.create(BloomeryMenu::new));
    public static final RegistryObject<MenuType<ForgeBaseMenu>> FORGE = MENU_TYPES.register("forge", () -> IForgeContainerType.create(ForgeBaseMenu::new));

}
