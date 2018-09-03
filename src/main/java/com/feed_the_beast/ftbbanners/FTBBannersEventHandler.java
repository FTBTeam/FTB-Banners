package com.feed_the_beast.ftbbanners;

import com.feed_the_beast.ftbbanners.net.FTBBannersNetHandler;
import com.feed_the_beast.ftbbanners.net.MessageSyncBanners;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBBanners.MOD_ID)
public class FTBBannersEventHandler
{
	public static final Map<String, Banner> BANNERS = new HashMap<>();

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		FTBBannersNetHandler.NET.sendTo(new MessageSyncBanners(FTBBannersEventHandler.BANNERS.values()), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public static void onWorldSaved(WorldEvent.Save event)
	{
		if (!event.getWorld().isRemote && event.getWorld().provider.getDimension() == 0)
		{
			File folder = new File(event.getWorld().getSaveHandler().getWorldDirectory(), "data/ftbbanners");

			if (!folder.exists())
			{
				folder.mkdirs();
			}

			for (Banner banner : BANNERS.values())
			{
				if (banner.shouldSave)
				{
					banner.save(folder);
					banner.shouldSave = false;
				}
			}
		}
	}
}