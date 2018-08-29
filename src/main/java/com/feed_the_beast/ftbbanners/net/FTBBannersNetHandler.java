package com.feed_the_beast.ftbbanners.net;

import com.feed_the_beast.ftbbanners.FTBBanners;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author LatvianModder
 */
public class FTBBannersNetHandler
{
	public static SimpleNetworkWrapper NET;

	public static void init()
	{
		NET = new SimpleNetworkWrapper(FTBBanners.MOD_ID);
		NET.registerMessage(new MessageSyncBanners(), MessageSyncBanners.class, 1, Side.CLIENT);
		NET.registerMessage(new MessageSyncOneBanner(), MessageSyncOneBanner.class, 2, Side.CLIENT);
		NET.registerMessage(new MessageToggleBanner(), MessageToggleBanner.class, 3, Side.CLIENT);
	}
}