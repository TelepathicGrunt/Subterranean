package net.telepathicgrunt.subterranean.world.dimension;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.subterranean.Subterranean;
import net.telepathicgrunt.subterranean.generation.STBiomeProvider;
import net.telepathicgrunt.subterranean.generation.STChunkGenerator;


@Mod.EventBusSubscriber(modid = Subterranean.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class STWorldProvider extends Dimension
{

	public STWorldProvider(World world, DimensionType typeIn)
	{
		super(world, typeIn, 1.0f); //set 1.0f. I think it has to do with maximum brightness?

		/**
		 * Creates the light to brightness table. It changes how light levels looks to the players but does not change the
		 * actual values of the light levels.
		 */
		for (int i = 0; i <= 15; ++i)
		{
			if (i < 1)
			{
				this.lightBrightnessTable[i] = (float) 1.2 / 20.0F;
			}
			else
			{
				this.lightBrightnessTable[i] = i / 20.0F;
			}
		}
	}


	@Override
	public int getSeaLevel()
	{
		return 24;
	}


	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		BlockPos blockpos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 90, 8));
		return this.world.getGroundAboveSeaLevel(blockpos).getMaterial().blocksMovement() ? blockpos : null;
	}


	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		return this.findSpawn(new ChunkPos(posX, posZ), checkValid);
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0.5f;
	}


	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isSkyColored()
	{
		return true;
	}


	@Override
	public boolean hasSkyLight()
	{
		return false;
	}

	
	/**
	 * Returns array with sunrise/sunset colors
	 */
	@Override
	@Nullable
	@OnlyIn(Dist.CLIENT)
	public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
	{
		return null;
	}


	/**
	 * the y level at which clouds are rendered.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getCloudHeight()
	{
		return -1;
	}


	@Override
	public int getActualHeight()
	{
		return 256;
	}


	@Override
	public boolean isNether()
	{
		return false;
	}


	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}


	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		return new Vec3d(0f, 0f, 0);
	}


	/**
	 * Returns a double value representing the Y value relative to the top of the map at which void fog is at its maximum.
	 * for example, means the void fog will be at its maximum at 256 here.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public double getVoidFogYFactor()
	{
		return 0; //set to 256 when done
	}


	@Override
	public boolean canRespawnHere()
	{
		return false;
	}


	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return false; //set to true when done
	}


	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		return new STChunkGenerator(world, new STBiomeProvider(world), ChunkGeneratorType.SURFACE.createSettings());
	}
}
