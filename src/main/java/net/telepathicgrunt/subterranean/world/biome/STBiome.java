package net.telepathicgrunt.subterranean.world.biome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.PodzolSurfaceBuilder;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.LargeStalactiteSurfaceBuilder;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.SmallStalactiteSurfaceBuilder;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.StoneSurfaceBuilder;

public class STBiome extends Biome {

    protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected static final BlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
    protected static final BlockState DIRT = Blocks.DIRT.getDefaultState();
    protected static final BlockState CLAY = Blocks.CLAY.getDefaultState();
    protected static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
    protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    protected static final BlockState LIGHT_GRAY_CONCRETE = Blocks.LIGHT_GRAY_CONCRETE.getDefaultState();
    protected static final BlockState LIGHT_GRAY_CONCRETE_POWDER = Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultState();
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
    
    public static final SurfaceBuilderConfig STONE_ANDESITE_GRAVEL_SURFACE_CONFIG = new SurfaceBuilderConfig(STONE, ANDESITE, GRAVEL);
    public static final SurfaceBuilderConfig COARSE_DIRT_ANDESITE_GRAVEL_CONFIG = new SurfaceBuilderConfig(COARSE_DIRT, ANDESITE, GRAVEL);
    public static final SurfaceBuilderConfig STONE_STONE_STONE_SURFACE_CONFIG = new SurfaceBuilderConfig(STONE, STONE, STONE);
    public static final SurfaceBuilderConfig ANDESITE_GRAVEL_STONE_SURFACE_CONFIG = new SurfaceBuilderConfig(ANDESITE, GRAVEL, STONE);
    public static final SurfaceBuilderConfig GRAVEL_GRAVEL_STONE_SURFACE_CONFIG = new SurfaceBuilderConfig(GRAVEL, GRAVEL, STONE);
    public static final SurfaceBuilderConfig LIGHT_GRAY_CONCRETE_POWDER_ANDESITE_STONE_SURFACE_CONFIG = new SurfaceBuilderConfig(LIGHT_GRAY_CONCRETE_POWDER, ANDESITE, STONE);
    
    public static final SurfaceBuilder<SurfaceBuilderConfig> STONE_SURFACE_BUILDER = new StoneSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> PODZOL_SURFACE_BUILDER = new PodzolSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> LARGE_STALACTITE_SURFACE_BUILDER = new LargeStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final SurfaceBuilder<SurfaceBuilderConfig> SMALL_STALACTITE_SURFACE_BUILDER = new SmallStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    
	protected STBiome(Biome.Builder biomeBuilder) {
		super(biomeBuilder);
	}
	
	
}
