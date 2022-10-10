package net.jmb19905.medievalworlds.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MWUtil {

    public static List<Recipe<?>> findRecipeByTypeAsList(RecipeType<?> type, Level level) {
        return new ArrayList<>(MWUtil.findRecipeByType(type, level));
    }

    @SuppressWarnings("unchecked")
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn, Level world) {
        return world != null ? world.getRecipeManager().getRecipes()
                .stream()
                .filter(recipe -> recipe.getType() == typeIn)
                .collect(Collectors.toSet())
                : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn) {
        ClientLevel world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }
}
