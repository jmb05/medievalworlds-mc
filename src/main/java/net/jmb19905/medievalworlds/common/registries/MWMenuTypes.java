package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.AlloyFurnaceMenu;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.menus.CustomFletchingMenu;
import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MenuType<CustomAnvilMenu>> ANVIL = MENU_TYPES.register("anvil", () -> IForgeMenuType.create(CustomAnvilMenu::new));
    public static final RegistryObject<MenuType<AlloyFurnaceMenu>> ALLOY = MENU_TYPES.register("alloy", () -> IForgeMenuType.create(AlloyFurnaceMenu::new));
    public static final RegistryObject<MenuType<CustomSmithingMenu>> SMITHING = MENU_TYPES.register("smithing", () -> IForgeMenuType.create(CustomSmithingMenu::new));
    public static final RegistryObject<MenuType<CustomFletchingMenu>> FLETCHING = MENU_TYPES.register("fletching", () -> IForgeMenuType.create(CustomFletchingMenu::new));

}
