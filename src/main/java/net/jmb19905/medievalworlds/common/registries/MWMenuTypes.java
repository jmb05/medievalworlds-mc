package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.menus.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MenuType<MWAnvilMenu>> ANVIL = MENU_TYPES.register("anvil", () -> IForgeMenuType.create(MWAnvilMenu::new));
    public static final RegistryObject<MenuType<AlloyFurnaceMenu>> ALLOY = MENU_TYPES.register("alloy", () -> IForgeMenuType.create(AlloyFurnaceMenu::new));
    public static final RegistryObject<MenuType<MWSmithingMenu>> SMITHING = MENU_TYPES.register("smithing", () -> IForgeMenuType.create(MWSmithingMenu::new));
    public static final RegistryObject<MenuType<MWFletchingMenu>> FLETCHING = MENU_TYPES.register("fletching", () -> IForgeMenuType.create(MWFletchingMenu::new));
    public static final RegistryObject<MenuType<SmelteryMenu>> SMELTERY = MENU_TYPES.register("smeltery", () -> IForgeMenuType.create(SmelteryMenu::new));
    public static final RegistryObject<MenuType<HearthMenu>> HEARTH = MENU_TYPES.register("hearth", () -> IForgeMenuType.create(HearthMenu::new));

}
