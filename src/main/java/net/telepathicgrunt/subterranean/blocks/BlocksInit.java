package net.telepathicgrunt.subterranean.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.telepathicgrunt.subterranean.Subterranean;


public class BlocksInit
{
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Subterranean.MODID);

    public static final RegistryObject<Block> SUBTERRANEAN_PORTAL = BLOCKS.register("subterranean_portal",
            () -> new STPortalBlock()
    );
    
	//creative tab to hold our block items
	public static final ItemGroup SUBTERRANEAN = new ItemGroup(ItemGroup.GROUPS.length, Subterranean.MODID)
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(SUBTERRANEAN_PORTAL.get());
		}
	};

	/**
	 * registers the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add blocks to
	 */
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new STPortalBlock());
	}


	/**
	 * registers the item version of the Blocks so they now exist in the registry
	 * 
	 * @param event - registry to add items to
	 */
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new BlockItem(BlocksInit.SUBTERRANEAN_PORTAL.get(), new Item.Properties().group(SUBTERRANEAN)).setRegistryName("subterranean_portal"));
	}

}