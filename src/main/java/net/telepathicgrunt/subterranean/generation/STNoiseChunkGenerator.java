package net.telepathicgrunt.subterranean.generation;

import java.util.Random;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.telepathicgrunt.subterranean.features.carvers.STCarvers;


public abstract class STNoiseChunkGenerator<T extends GenerationSettings> extends ChunkGenerator<T>
{

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();

	private static final float[] field_222561_h = Util.make(new float[13824], (p_222557_0_) ->
	{
		for (int i = 0; i < 24; ++i)
		{
			for (int j = 0; j < 24; ++j)
			{
				for (int k = 0; k < 24; ++k)
				{
					p_222557_0_[i * 24 * 24 + j * 24 + k] = (float) func_222554_b(j - 12, k - 12, i - 12);
				}
			}
		}

	});
	private final int verticalNoiseGranularity;
	private final int horizontalNoiseGranularity;
	private final int noiseSizeX;
	private final int noiseSizeY;
	private final int noiseSizeZ;
	protected final SharedSeedRandom randomSeed;
	private final OctavesNoiseGenerator minNoise;
	private final OctavesNoiseGenerator maxNoise;
	private final OctavesNoiseGenerator mainNoise;
	private final INoiseGenerator surfaceDepthNoise;
	protected final BlockState defaultBlock;
	protected final BlockState defaultFluid;


	public STNoiseChunkGenerator(IWorld p_i49931_1_, BiomeProvider p_i49931_2_, int horizontalNoiseGranularityIn, int verticalNoiseGranularityIn, int heightRange, T p_i49931_6_)
	{
		super(p_i49931_1_, p_i49931_2_, p_i49931_6_);
		this.verticalNoiseGranularity = verticalNoiseGranularityIn;
		this.horizontalNoiseGranularity = horizontalNoiseGranularityIn;
		this.defaultBlock = STONE;
		this.defaultFluid = WATER;
		this.noiseSizeX = 16 / this.horizontalNoiseGranularity;
		this.noiseSizeY = heightRange / this.verticalNoiseGranularity;
		this.noiseSizeZ = 16 / this.horizontalNoiseGranularity;
		this.randomSeed = new SharedSeedRandom(this.seed);
		this.minNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
		this.maxNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
		this.mainNoise = new OctavesNoiseGenerator(this.randomSeed, 7, 0);
		this.surfaceDepthNoise = (new PerlinNoiseGenerator(this.randomSeed, 3, 0));
	}


	private double internalSetupPerlinNoiseGenerators(int x, int y, int z, double getCoordinateScale, double getHeightScale, double getMainCoordinateScale, double getMainHeightScale, double rangeMaybe)
	{
		double d0 = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 1.0D;

		for (int i = 0; i < 16; ++i)
		{
			double limitX = OctavesNoiseGenerator.maintainPrecision(x * getCoordinateScale * d3);
			double limitY = OctavesNoiseGenerator.maintainPrecision(y * getHeightScale * d3);
			double limitZ = OctavesNoiseGenerator.maintainPrecision(z * getCoordinateScale * d3);

			double mainX = OctavesNoiseGenerator.maintainPrecision(x * getMainCoordinateScale * d3);
			double mainY = OctavesNoiseGenerator.maintainPrecision(y * getMainHeightScale * d3);
			double mainZ = OctavesNoiseGenerator.maintainPrecision(z * getMainCoordinateScale * d3);

			double d7 = 684.412F * d3;
			d0 += this.minNoise.getOctave(i).func_215456_a(limitX, limitY, limitZ, d7, y * d7);
			d1 += this.maxNoise.getOctave(i).func_215456_a(limitX, limitY, limitZ, d7, y * d7);
			if (i < 8)
			{
				d2 += this.mainNoise.getOctave(i).func_215456_a(mainX, mainY, mainZ, rangeMaybe * d3, y * rangeMaybe * d3) / d3;
			}

			d3 /= 2.0D;
		}

		return MathHelper.clampedLerp(d0 / 512.0D, d1 / 512.0D, (d2 / 10.0D + 1.0D) / 2.0D);
	}


	protected double[] func_222547_b(int p_222547_1_, int p_222547_2_)
	{
		double[] adouble = new double[this.noiseSizeY + 1];
		this.fillNoiseColumn(adouble, p_222547_1_, p_222547_2_);
		return adouble;
	}


	protected void setupPerlinNoiseGenerators(double[] areaArrayIn, int x, int z, double getCoordinateScale, double getHeightScale, double getMainCoordinateScale, double getMainHeightScale, double rangeMaybe, int divisor, int lerpValue)
	{
		double[] localAreaArray = this.getBiomeNoiseColumn(x, z);
		double nearbyArea1 = localAreaArray[0];
		double nearbyArea2 = localAreaArray[1];
		double maxHeight = this.maxheight();
		double minHeight = this.minHeight();

		for (int y = 0; y < this.noiseSizeY(); ++y)
		{
			double valueAtY = this.internalSetupPerlinNoiseGenerators(x, y, z, getCoordinateScale, getHeightScale, getMainCoordinateScale, getMainHeightScale, rangeMaybe);
			valueAtY = valueAtY - this.biomeHeightSmoother(nearbyArea1, nearbyArea2, y);

			if (y > maxHeight)
			{
				valueAtY = MathHelper.clampedLerp(valueAtY, lerpValue, y - maxHeight);
			}
			else if (y < minHeight)
			{
				valueAtY = MathHelper.clampedLerp(valueAtY, -20000D, 0.000025D);
			}

			areaArrayIn[y] = valueAtY;
		}

	}


	protected abstract double[] getBiomeNoiseColumn(int noiseX, int noiseZ);


	protected abstract double biomeHeightSmoother(double p_222545_1_, double p_222545_3_, int p_222545_5_);


	protected double maxheight()
	{
		return this.noiseSizeY() - 4;
	}


	protected double minHeight()
	{
		return 3.0D;
	}


	@Override
	public int func_222529_a(int chunkX, int chunkZ, Heightmap.Type heightmapType)
	{
		int i = Math.floorDiv(chunkX, this.horizontalNoiseGranularity);
		int j = Math.floorDiv(chunkZ, this.horizontalNoiseGranularity);
		int k = Math.floorMod(chunkX, this.horizontalNoiseGranularity);
		int l = Math.floorMod(chunkZ, this.horizontalNoiseGranularity);
		double d0 = (double) k / (double) this.horizontalNoiseGranularity;
		double d1 = (double) l / (double) this.horizontalNoiseGranularity;
		double[][] adouble = new double[][] { this.func_222547_b(i, j), this.func_222547_b(i, j + 1), this.func_222547_b(i + 1, j), this.func_222547_b(i + 1, j + 1) };
		int seaLevel = getSeaLevel();

		for (int j1 = this.noiseSizeY - 1; j1 >= 0; --j1)
		{
			double d2 = adouble[0][j1];
			double d3 = adouble[1][j1];
			double d4 = adouble[2][j1];
			double d5 = adouble[3][j1];
			double d6 = adouble[0][j1 + 1];
			double d7 = adouble[1][j1 + 1];
			double d8 = adouble[2][j1 + 1];
			double d9 = adouble[3][j1 + 1];

			for (int k1 = this.verticalNoiseGranularity - 1; k1 >= 0; --k1)
			{
				double d10 = (double) k1 / (double) this.verticalNoiseGranularity;
				double d11 = MathHelper.lerp3(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
				int y = j1 * this.verticalNoiseGranularity + k1;
				if (d11 > 0.0D || y < seaLevel)
				{
					BlockState blockstate;
					if (d11 > 0.0D)
					{
						blockstate = this.defaultBlock;
					}
					else
					{
						blockstate = this.defaultFluid;
					}

					if (heightmapType.getHeightLimitPredicate().test(blockstate))
					{
						return y + 1;
					}
				}
			}
		}

		return 0;
	}


	protected abstract void fillNoiseColumn(double[] p_222548_1_, int p_222548_2_, int p_222548_3_);


	public int noiseSizeY()
	{
		return this.noiseSizeY + 1;
	}


	@Override
	public void func_225551_a_(WorldGenRegion region, IChunk chunk)
	{
		ChunkPos chunkpos = chunk.getPos();
		int xChunk = chunkpos.x;
		int zChunk = chunkpos.z;
		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		sharedseedrandom.setBaseChunkSeed(xChunk, zChunk);
		ChunkPos chunkpos1 = chunk.getPos();
		int xPosOfChunk = chunkpos1.getXStart();
		int zPosOfChunk = chunkpos1.getZStart();
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (int xInChunk = 0; xInChunk < 16; ++xInChunk)
		{
			for (int zInChunk = 0; zInChunk < 16; ++zInChunk)
			{
				int xPos = xPosOfChunk + xInChunk;
				int zPos = zPosOfChunk + zInChunk;
				int yPos = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, xInChunk, zInChunk) + 1;
				double noise = this.surfaceDepthNoise.noiseAt(xPos * 0.0625D, zPos * 0.0625D, 0.0625D, xInChunk * 0.0625D) * 10.0D;
				region.getBiome(blockpos$mutable.setPos(xPosOfChunk + xInChunk, yPos, zPosOfChunk + zInChunk)).buildSurface(sharedseedrandom, chunk, xPos, zPos, yPos, noise, this.defaultBlock, this.defaultFluid, getSeaLevel(), this.world.getSeed());
			}
		}

		this.makeBedrock(chunk, sharedseedrandom);
	}


	protected void makeBedrock(IChunk chunk, Random random)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int i = chunk.getPos().getXStart();
		int j = chunk.getPos().getZStart();
		int bedrockFloorHeight = 0;
		int bedrockCeilingHeight = 255;

		for (BlockPos blockpos : BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15))
		{
			if (bedrockCeilingHeight > 0)
			{
				for (int i1 = bedrockCeilingHeight; i1 >= bedrockCeilingHeight - 4; --i1)
				{
					if (i1 >= bedrockCeilingHeight - random.nextInt(5))
					{
						chunk.setBlockState(blockpos$Mutable.setPos(blockpos.getX(), i1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
					}
				}
			}

			if (bedrockFloorHeight < 256)
			{
				for (int j1 = bedrockFloorHeight + 4; j1 >= bedrockFloorHeight; --j1)
				{
					if (j1 <= bedrockFloorHeight + random.nextInt(5))
					{
						chunk.setBlockState(blockpos$Mutable.setPos(blockpos.getX(), j1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
					}
				}
			}
		}

	}


	@Override
	public void makeBase(IWorld world, IChunk chunk)
	{
		ObjectList<AbstractVillagePiece> objectlist = new ObjectArrayList<>(10);
		ObjectList<JigsawJunction> objectlist1 = new ObjectArrayList<>(32);
		ChunkPos chunkpos = chunk.getPos();
		int chunkX = chunkpos.x;
		int chunkZ = chunkpos.z;
		int coordinateX = chunkX << 4;
		int coordinateZ = chunkZ << 4;
//
//		for (Structure<?> structure : Feature.ILLAGER_STRUCTURES)
//		{
//			String s = structure.getStructureName();
//			LongIterator longiterator = chunk.getStructureReferences(s).iterator();
//
//			while (longiterator.hasNext())
//			{
//				long j1 = longiterator.nextLong();
//				ChunkPos chunkpos1 = new ChunkPos(j1);
//				IChunk ichunk = world.getChunk(chunkpos1.x, chunkpos1.z);
//				StructureStart structurestart = ichunk.getStructureStart(s);
//				if (structurestart != null && structurestart.isValid())
//				{
//					for (StructurePiece structurepiece : structurestart.getComponents())
//					{
//						if (structurepiece.func_214810_a(chunkpos, 12) && structurepiece instanceof AbstractVillagePiece)
//						{
//							AbstractVillagePiece abstractvillagepiece = (AbstractVillagePiece) structurepiece;
//							JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = abstractvillagepiece.getJigsawPiece().getPlacementBehaviour();
//							if (jigsawpattern$placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID)
//							{
//								objectlist.add(abstractvillagepiece);
//							}
//
//							for (JigsawJunction jigsawjunction : abstractvillagepiece.getJunctions())
//							{
//								int k1 = jigsawjunction.getSourceX();
//								int l1 = jigsawjunction.getSourceZ();
//								if (k1 > coordinateX - 12 && l1 > coordinateZ - 12 && k1 < coordinateX + 15 + 12 && l1 < coordinateZ + 15 + 12)
//								{
//									objectlist1.add(jigsawjunction);
//								}
//							}
//						}
//					}
//				}
//			}
//		}

		double[][][] adouble = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

		for (int j5 = 0; j5 < this.noiseSizeZ + 1; ++j5)
		{
			adouble[0][j5] = new double[this.noiseSizeY + 1];
			this.fillNoiseColumn(adouble[0][j5], chunkX * this.noiseSizeX, chunkZ * this.noiseSizeZ + j5);
			adouble[1][j5] = new double[this.noiseSizeY + 1];
		}

		ChunkPrimer chunkprimer = (ChunkPrimer) chunk;
		Heightmap heightmap = chunkprimer.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap heightmap1 = chunkprimer.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		ObjectListIterator<AbstractVillagePiece> objectlistiterator = objectlist.iterator();
		ObjectListIterator<JigsawJunction> objectlistiterator1 = objectlist1.iterator();

		for (int k5 = 0; k5 < this.noiseSizeX; ++k5)
		{
			for (int l5 = 0; l5 < this.noiseSizeZ + 1; ++l5)
			{
				this.fillNoiseColumn(adouble[1][l5], chunkX * this.noiseSizeX + k5 + 1, chunkZ * this.noiseSizeZ + l5);
			}

			for (int i6 = 0; i6 < this.noiseSizeZ; ++i6)
			{
				ChunkSection chunksection = chunkprimer.getSection(15);
				chunksection.lock();

				for (int j6 = this.noiseSizeY - 1; j6 >= 0; --j6)
				{
					double d16 = adouble[0][i6][j6];
					double d17 = adouble[0][i6 + 1][j6];
					double d18 = adouble[1][i6][j6];
					double d0 = adouble[1][i6 + 1][j6];
					double d1 = adouble[0][i6][j6 + 1];
					double d2 = adouble[0][i6 + 1][j6 + 1];
					double d3 = adouble[1][i6][j6 + 1];
					double d4 = adouble[1][i6 + 1][j6 + 1];

					for (int i2 = this.verticalNoiseGranularity - 1; i2 >= 0; --i2)
					{
						int currentY = j6 * this.verticalNoiseGranularity + i2;
						int y = currentY & 15;
						int l2 = currentY >> 4;
						if (chunksection.getYLocation() >> 4 != l2)
						{
							chunksection.unlock();
							chunksection = chunkprimer.getSection(l2);
							chunksection.lock();
						}

						double d5 = (double) i2 / (double) this.verticalNoiseGranularity;
						double d6 = MathHelper.lerp(d5, d16, d1);
						double d7 = MathHelper.lerp(d5, d18, d3);
						double d8 = MathHelper.lerp(d5, d17, d2);
						double d9 = MathHelper.lerp(d5, d0, d4);

						for (int i3 = 0; i3 < this.horizontalNoiseGranularity; ++i3)
						{
							int j3 = coordinateX + k5 * this.horizontalNoiseGranularity + i3;
							int x = j3 & 15;
							double d10 = (double) i3 / (double) this.horizontalNoiseGranularity;
							double d11 = MathHelper.lerp(d10, d6, d7);
							double d12 = MathHelper.lerp(d10, d8, d9);

							for (int l3 = 0; l3 < this.horizontalNoiseGranularity; ++l3)
							{
								int i4 = coordinateZ + i6 * this.horizontalNoiseGranularity + l3;
								int z = i4 & 15;
								double d13 = (double) l3 / (double) this.horizontalNoiseGranularity;
								double d14 = MathHelper.lerp(d13, d11, d12);
								double d15 = MathHelper.clamp(d14 / 200.0D, -1.0D, 1.0D);

								int k4;
								int l4;
								int i5;
								for (d15 = d15 / 2.0D - d15 * d15 * d15 / 24.0D; objectlistiterator.hasNext(); d15 += func_222556_a(k4, l4, i5) * 0.8D)
								{
									AbstractVillagePiece abstractvillagepiece1 = objectlistiterator.next();
									MutableBoundingBox mutableboundingbox = abstractvillagepiece1.getBoundingBox();
									k4 = Math.max(0, Math.max(mutableboundingbox.minX - j3, j3 - mutableboundingbox.maxX));
									l4 = currentY - (mutableboundingbox.minY + abstractvillagepiece1.getGroundLevelDelta());
									i5 = Math.max(0, Math.max(mutableboundingbox.minZ - i4, i4 - mutableboundingbox.maxZ));
								}

								objectlistiterator.back(objectlist.size());

								while (objectlistiterator1.hasNext())
								{
									JigsawJunction jigsawjunction1 = objectlistiterator1.next();
									int k6 = j3 - jigsawjunction1.getSourceX();
									k4 = currentY - jigsawjunction1.getSourceGroundY();
									l4 = i4 - jigsawjunction1.getSourceZ();
									d15 += func_222556_a(k6, k4, l4) * 0.4D;
								}

								objectlistiterator1.back(objectlist1.size());
								BlockState blockstate;

								if (d15 > 0.0D)
								{
									//place the biome's solid block
									blockstate = this.defaultBlock;
								}
								else if (currentY < getSeaLevel())
								{
									blockstate = this.defaultFluid;
								}
								else
								{
									blockstate = STCarvers.AIR;
								}

								if (blockstate != STCarvers.AIR)
								{
									if (blockstate.getLightValue() != 0)
									{
										blockpos$Mutable.setPos(j3, currentY, i4);
										chunkprimer.addLightPosition(blockpos$Mutable);
									}

									chunksection.setBlockState(x, y, z, blockstate, false);
									heightmap.update(x, currentY, z, blockstate);
									heightmap1.update(x, currentY, z, blockstate);
								}
							}
						}
					}
				}

				chunksection.unlock();
			}

			double[][] adouble1 = adouble[0];
			adouble[0] = adouble[1];
			adouble[1] = adouble1;
		}

	}


	private static double func_222556_a(int p_222556_0_, int p_222556_1_, int p_222556_2_)
	{
		int i = p_222556_0_ + 12;
		int j = p_222556_1_ + 12;
		int k = p_222556_2_ + 12;
		if (i >= 0 && i < 24)
		{
			if (j >= 0 && j < 24)
			{
				return k >= 0 && k < 24 ? (double) field_222561_h[k * 24 * 24 + i * 24 + j] : 0.0D;
			}
			else
			{
				return 0.0D;
			}
		}
		else
		{
			return 0.0D;
		}
	}


	private static double func_222554_b(int p_222554_0_, int p_222554_1_, int p_222554_2_)
	{
		double d0 = p_222554_0_ * p_222554_0_ + p_222554_2_ * p_222554_2_;
		double d1 = p_222554_1_ + 0.5D;
		double d2 = d1 * d1;
		double d3 = Math.pow(Math.E, -(d2 / 16.0D + d0 / 16.0D));
		double d4 = -d1 * MathHelper.fastInvSqrt(d2 / 2.0D + d0 / 2.0D) / 2.0D;
		return d4 * d3;
	}
}