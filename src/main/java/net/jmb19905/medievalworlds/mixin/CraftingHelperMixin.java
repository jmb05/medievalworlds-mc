package net.jmb19905.medievalworlds.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingHelper.class)
public class CraftingHelperMixin {

    @Inject(at = @At("HEAD"), method = "getItemStack(Lcom/google/gson/JsonObject;ZZ)Lnet/minecraft/world/item/ItemStack;")
    private static void getItemStack(JsonObject json, boolean readNBT, boolean disallowsAirInRecipe, CallbackInfoReturnable<ItemStack> cir) {
        System.err.println("Skipping recipe with air as result");
        String itemName = GsonHelper.getAsString(json, "item");
        ResourceLocation itemKey = new ResourceLocation(itemName);

        if (!ForgeRegistries.ITEMS.containsKey(itemKey))
            throw new JsonSyntaxException("Unknown item '" + itemName + "'");

        Item item = ForgeRegistries.ITEMS.getValue(itemKey);
        if (disallowsAirInRecipe && item == Items.AIR) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

}
