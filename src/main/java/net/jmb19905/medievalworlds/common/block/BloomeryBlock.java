package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.TileEntityTypeRegistryHandler;
import net.jmb19905.medievalworlds.common.tileentites.BloomeryTileEntity;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class BloomeryBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLAY = BooleanProperty.create("clay");

    public BloomeryBlock(Properties properties) {
        super(properties);
    }

    public static class Bottom extends BloomeryBlock{

        public Bottom() {
            super(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestLevel(0).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.5F).setLightLevel(value -> 13));
            this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(CLAY, true));
        }

        @Override
        public boolean hasTileEntity(BlockState state) {
            return true;
        }

        @Override
        public TileEntity createTileEntity(BlockState state, IBlockReader world) {
            return TileEntityTypeRegistryHandler.BLOOMERY_BOTTOM.get().create();
        }

        @Override
        public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return BOTTOM_SHAPE;
        }
    }

    public static class Top extends BloomeryBlock{

        public Top() {
            super(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestLevel(0).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.5F).setLightLevel(value -> 13));
            this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(CLAY, true));
        }

        @Override
        public boolean hasTileEntity(BlockState state) {
            return true;
        }

        @Override
        public TileEntity createTileEntity(BlockState state, IBlockReader world) {
            return TileEntityTypeRegistryHandler.BLOOMERY_TOP.get().create();
        }

        @Override
        public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return TOP_SHAPE;
        }
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, CLAY);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        //return state.get(LIT) ? super.getLightValue(state, world, pos) : 0;
        return 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if(stack.hasDisplayName()){
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile instanceof BloomeryTileEntity.Bottom){
                ((BloomeryTileEntity.Bottom) tile).setCustomName(stack.getDisplayName());
            }
        }
        BlockPos topPosition = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        worldIn.setBlockState(topPosition, BlockRegistryHandler.BLOOMERY_TOP_BLOCK.get().getDefaultState());
    }

    @Override
    public boolean hasComparatorInputOverride(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(@Nonnull BlockState blockState, World worldIn, @Nonnull BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if(!worldIn.isRemote){
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile instanceof BloomeryTileEntity.Bottom){
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof BloomeryTileEntity.Bottom && state.getBlock() != newState.getBlock()){
            BloomeryTileEntity.Bottom bloomeryTile = (BloomeryTileEntity.Bottom)tile;
            ((CustomItemHandler) bloomeryTile.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addEntity(itemEntity);
            });
        }

        if(state.hasTileEntity() && state.getBlock() != newState.getBlock()){
            worldIn.removeTileEntity(pos);
        }
    }

    private static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);

    private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 14, 15);

}
