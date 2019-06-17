package com.feed_the_beast.ftbbanners;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBBanners.MOD_ID, value = Side.CLIENT)
public class FTBBannersClientEventHandler
{
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(FTBBanners.BANNER_BLOCK_ITEM, 0, new ModelResourceLocation(FTBBanners.BANNER_BLOCK_ITEM.getRegistryName(), "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(BannerBlockEntity.class, new BannerBlockRenderer());
	}
}