package net.jmb19905.medievalworlds.block;

import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class AbstractOreBlock extends OreBlock {

    private final int minXp;
    private final int maxXp;

    public AbstractOreBlock(Properties properties, int minXp, int maxXp) {
        super(properties);
        this.minXp = minXp;
        this.maxXp = maxXp;
    }

    public AbstractOreBlock(Properties properties, int xp) {
        super(properties);
        this.minXp = xp;
        this.maxXp = xp;
    }

    @Override
    public int getExpDrop(@Nonnull BlockState state, @Nonnull IWorldReader reader, @Nonnull BlockPos pos, int fortune, int silktouch) {
        if(minXp >= maxXp) {
            return 0;
        }else{
            return Util.getRandomNumberInRange(minXp, maxXp);
        }
    }
}
