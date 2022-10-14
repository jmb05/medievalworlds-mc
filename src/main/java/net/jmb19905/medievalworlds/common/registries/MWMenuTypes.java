package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MenuType<CustomAnvilMenu>> ANVIL_MENU = MENU_TYPES.register("anvil_menu", () -> IForgeMenuType.create(CustomAnvilMenu::new));


}
