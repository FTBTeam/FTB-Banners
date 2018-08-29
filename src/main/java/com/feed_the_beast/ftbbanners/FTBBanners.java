package com.feed_the_beast.ftbbanners;

import com.feed_the_beast.ftbbanners.command.CommandBannerTree;
import com.feed_the_beast.ftbbanners.net.FTBBannersNetHandler;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.io.FileInputStream;

@Mod(
		modid = FTBBanners.MOD_ID,
		name = FTBBanners.MOD_NAME,
		version = FTBBanners.VERSION
)
public class FTBBanners
{
	public static final String MOD_ID = "ftbbanners";
	public static final String MOD_NAME = "FTB Banners";
	public static final String VERSION = "0.0.0.ftbbanners";

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		FTBBannersNetHandler.init();
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandBannerTree());
	}

	@Mod.EventHandler
	public void onServerStarted(FMLServerStartedEvent event)
	{
		FTBBannersEventHandler.BANNERS.clear();

		File file = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "data/ftbbanners");

		if (file.exists() && file.isDirectory())
		{
			File[] files = file.listFiles();

			if (files != null && files.length > 0)
			{
				for (File file1 : files)
				{
					String name = file1.getName();

					if (name.endsWith(".nbt"))
					{
						try (FileInputStream in = new FileInputStream(file1))
						{
							NBTTagCompound nbt = CompressedStreamTools.readCompressed(in);
							Banner banner = new Banner(name.substring(0, name.length() - 4));
							banner.read(nbt);
							FTBBannersEventHandler.BANNERS.put(banner.id, banner);
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
}