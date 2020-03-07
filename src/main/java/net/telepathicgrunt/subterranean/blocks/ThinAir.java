package net.telepathicgrunt.subterranean.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;


public class ThinAir extends Block
{

	public static final IntegerProperty DISTANCE = BlockStateProperties.AGE_0_25;


	public ThinAir()
	{
		super(Block.Properties.create(Material.AIR, MaterialColor.AIR).doesNotBlockMovement().noDrops());
		this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, Integer.valueOf(25)));
		setRegistryName("thin_air");
	}

	@Override
	public BlockRenderType getRenderType(BlockState blockState)
	{
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, ISelectionContext context)
	{
		return VoxelShapes.empty();
	}

	@Override
	public boolean isAir(BlockState blockState)
	{
		return true;
	}


	/**
	 * Amount of light emitted
	 * 
	 * @deprecated prefer calling {@link IBlockState#getLightValue()}
	 */
	@Override
	public int getLightValue(BlockState blockState)
	{
		return blockState.get(DISTANCE) < 25 ? (int)Math.ceil((25D-blockState.get(DISTANCE))*0.6D) : 0;
	}

	@Override
	public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random)
	{
		serverWorld.setBlockState(blockPos, updateDistance(blockState, serverWorld, blockPos), 3);
	}

	
	private static BlockState updateDistance(BlockState blockState, IWorld world, BlockPos blockPos)
	{
		int currentDistance = 25;

		try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain())
		{
			for (Direction direction : Direction.values())
			{
				blockpos$pooledmutable.setPos(blockPos).move(direction);
				currentDistance = Math.min(currentDistance, getDistance(world.getBlockState(blockpos$pooledmutable)) + 1);
				if (currentDistance == 0)
				{
					break;
				}
			}
		}

		return blockState.with(DISTANCE, Integer.valueOf(currentDistance));
	}

	
	private static int getDistance(BlockState blockState)
	{
		return blockState.getBlock() instanceof ThinAir ? blockState.get(DISTANCE) : 25;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockState)
	{
		blockState.add(DISTANCE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext blockState)
	{
		return updateDistance(this.getDefaultState(), blockState.getWorld(), blockState.getPos());
	}


	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state. For example,
	 * fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its
	 * solidified counterpart. Note that this method should ideally consider only the specific face passed in.
	 */
	@Override
	public BlockState updatePostPlacement(BlockState blockState1, Direction direction, BlockState blockState2, IWorld world, BlockPos blockPos1, BlockPos blockPos2)
	{
		int i = getDistance(blockState2) + 1;
		if (i != 0 || blockState1.get(DISTANCE) != 0)
		{
			world.getPendingBlockTicks().scheduleTick(blockPos1, this, 1);
		}

		return blockState1;
	}
}
