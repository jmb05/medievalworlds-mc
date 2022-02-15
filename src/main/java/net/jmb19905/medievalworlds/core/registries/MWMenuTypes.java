package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.AlloyFurnaceMenu;
import net.jmb19905.medievalworlds.common.menus.AnvilMenu;
import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE = MENU_TYPES.register("alloy_furnace", () -> IForgeMenuType.create(AlloyFurnaceMenu::new));
    public static final RegistryObject<MenuType<AnvilMenu>> ANVIL_MENU = MENU_TYPES.register("anvil_menu", () -> IForgeMenuType.create(AnvilMenu::new));
    public static final RegistryObject<MenuType<CustomSmithingMenu>> SMITHING_MENU = MENU_TYPES.register("smithing_menu", () -> IForgeMenuType.create((windowId, inv, data) -> new CustomSmithingMenu(windowId, inv)));

}
