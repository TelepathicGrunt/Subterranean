package net.telepathicgrunt.subterranean.world.biome;


import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.subterranean.Subterranean;
import net.telepathicgrunt.subterranean.world.biomes.CenteralIslesBiome;
import net.telepathicgrunt.subterranean.world.biomes.TestBiome;
import net.telepathicgrunt.subterranean.world.biomes.WaterFloorBiome;

public class BiomeInit {


	public static Biome CENTERAL_ISLES_BIOME = new CenteralIslesBiome();
	public static Biome WATER_FLOOR_BIOME = new WaterFloorBiome();
	public static Biome TEST_BIOME = new TestBiome();
	
	
	//registers the biomes so they now exist in the registry along with their types
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {

   	    IForgeRegistry<Biome> registry = event.getRegistry();
		
		initBiome(registry, CENTERAL_ISLES_BIOME, "Centeral Isles", BiomeType.COOL, Type.PLAINS);
		initBiome(registry, WATER_FLOOR_BIOME, "Water Floor", BiomeType.COOL, Type.OCEAN);
		initBiome(registry, TEST_BIOME, "Test Biome", BiomeType.COOL, Type.PLAINS);
		
	}



	//adds biome to registry with their type to the registry and to the biome dictionary
	private static Biome initBiome(IForgeRegistry<Biome> registry, Biome biome, String name, BiomeType biomeType, Type... types) {
		Subterranean.register(registry, biome, name);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}
}
