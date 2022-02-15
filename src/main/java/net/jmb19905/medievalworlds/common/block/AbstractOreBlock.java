package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;

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
    public int getExpDrop(@Nonnull BlockState state, @Nonnull LevelReader reader, @Nonnull BlockPos pos, int fortune, int silk) {
        if(minXp >= maxXp) {
            return 0;
        }else{
            return Util.getRandomIntInRange(minXp, maxXp);
        }
    }
}
