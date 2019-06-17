package com.feed_the_beast.ftbbanners;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBBanners.MOD_ID)
public class FTBBannersEventHandler
{
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BannerBlock().setRegistryName("banner_block"));
		GameRegistry.registerTileEntity(BannerBlockEntity.class, new ResourceLocation(FTBBanners.MOD_ID, "banner_block"));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(FTBBanners.BANNER_BLOCK).setRegistryName("banner_block"));
	}
}