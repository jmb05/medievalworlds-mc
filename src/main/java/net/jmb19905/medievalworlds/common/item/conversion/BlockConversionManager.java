package net.jmb19905.medievalworlds.common.item.conversion;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockConversionManager {

    private static final Map<BlockConverterItem, String> converters = new HashMap<>();
    private static final Map<String, List<Pair<IBlockConversionPredicate, IComplexBlockConversion>>> conversions = new HashMap<>();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void registerConverter(BlockConverterItem item, String name) {
        if (item == null || name == null) {
            LOGGER.warn("Ignoring invalid Converter (" + item + " -> " + name + ")");
            return;
        }
        converters.put(item, name);
    }

    public static void registerConversion(String name, IBlockConversionPredicate condition, IComplexBlockConversion conversion) {
        if (name == null || conversion == null) {
            LOGGER.warn("Ignoring invalid Conversion (" + name + " -> " + conversion + ")");
            return;
        }
        List<Pair<IBlockConversionPredicate, IComplexBlockConversion>> list = conversions.computeIfAbsent(name, k -> new ArrayList<>());
        list.add(new Pair<>(condition, conversion));
    }

    public static InteractionResult processConversions(BlockConverterItem item, UseOnContext ctx) {
        String name = converters.get(item);
        BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
        InteractionResult result = conversions.get(name)
                .stream()
                .filter(pair -> pair.getFirst().check(state, ctx.getClickedFace()))
                .map(Pair::getSecond)
                .findFirst()
                .orElse(c -> InteractionResult.PASS)
                .convert(ctx);
        Player player = ctx.getPlayer();
        ItemStack itemStack = ctx.getItemInHand();
        if (player != null && result.consumesAction()) player.getCooldowns().addCooldown(itemStack.getItem(), 10);
        return result;
    }

}
