package net.telepathicgrunt.subterranean.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.telepathicgrunt.subterranean.Subterranean;


public class STBlocks
{
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Subterranean.MODID);

    public static final RegistryObject<Block> THIN_AIR = BLOCKS.register("thin_air",
            () -> new ThinAir()
    );
    
	/**
	 * registers the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add blocks to
	 */
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new ThinAir());
	}
}