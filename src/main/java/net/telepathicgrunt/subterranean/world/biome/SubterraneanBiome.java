package net.telepathicgrunt.subterranean.world.biome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.GenericSurfaceBuilder;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.LargeStalactiteSurfaceBuilder;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.SmallStalactiteSurfaceBuilder;

public class SubterraneanBiome extends Biome {

    protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected static final BlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
    protected static final BlockState DIRT = Blocks.DIRT.getDefaultState();
    protected static final BlockState CLAY = Blocks.CLAY.getDefaultState();
    protected static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
    protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    protected static final BlockState GRAY_CONCRETE = Blocks.GRAY_CONCRETE.getDefaultState();
    protected static final BlockState GRAY_CONCRETE_POWDER = Blocks.GRAY_CONCRETE_POWDER.getDefaultState();
    protected static final BlockState GRAY_TERRACOTTA = Blocks.GRAY_TERRACOTTA.getDefaultState();
    protected static final BlockState DEAD_BRAIN_CORAL = Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState();
    protected static final BlockState DEAD_BUBBLE_CORAL = Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState();
    protected static final BlockState DEAD_HORN_CORAL = Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState();
    protected static final BlockState DEAD_TUBE_CORAL = Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState();
    protected static final BlockState DEAD_FIRE_CORAL = Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState();
    
    protected static final BlockState GRANITE = Blocks.GRANITE.getDefaultState();
    protected static final BlockState DIORITE = Blocks.DIORITE.getDefaultState();
    protected static final BlockState ANDESITE = Blocks.ANDESITE.getDefaultState();
    protected static final BlockState COAL_ORE = Blocks.COAL_ORE.getDefaultState();
    protected static final BlockState IRON_ORE = Blocks.IRON_ORE.getDefaultState();
    protected static final BlockState GOLD_ORE = Blocks.GOLD_ORE.getDefaultState();
    protected static final BlockState REDSTONE_ORE = Blocks.REDSTONE_ORE.getDefaultState();
    protected static final BlockState DIAMOND_ORE = Blocks.DIAMOND_ORE.getDefaultState();
    protected static final BlockState LAPIS_ORE = Blocks.LAPIS_ORE.getDefaultState();
    protected static final BlockState EMERALD_ORE = Blocks.EMERALD_ORE.getDefaultState();
    
    public static final SurfaceBuilderConfig GENERIC_SURFACE_CONFIG = new SurfaceBuilderConfig(STONE, ANDESITE, GRAVEL);
    public static final SurfaceBuilderConfig COARSE_DIRT_ANDESITE_GRAVEL_CONFIG = new SurfaceBuilderConfig(COARSE_DIRT, ANDESITE, GRAVEL);
    public static final SurfaceBuilderConfig STONE_STONE_STONE_SURFACE_CONFIG = new SurfaceBuilderConfig(STONE, STONE, STONE);

    public static final SurfaceBuilder<SurfaceBuilderConfig> GENERIC_SURFACE_BUILDER = new GenericSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> LARGE_STALACTITE_SURFACE_BUILDER = new LargeStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> SMALL_STALACTITE_SURFACE_BUILDER = new SmallStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    
	protected SubterraneanBiome(Biome.Builder biomeBuilder) {
		super(biomeBuilder);
	}
	
	
}
