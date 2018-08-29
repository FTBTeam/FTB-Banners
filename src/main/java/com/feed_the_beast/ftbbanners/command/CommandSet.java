package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import com.feed_the_beast.ftbbanners.net.FTBBannersNetHandler;
import com.feed_the_beast.ftbbanners.net.MessageToggleBanner;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

/**
 * @author LatvianModder
 */
public class CommandSet extends CommandBannerBase
{
	public CommandSet(String n)
	{
		super(n);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException(getUsage(sender));
		}

		Banner banner = FTBBannersEventHandler.BANNERS.get(args[0]);

		if (banner != null)
		{
			if (banner.alpha == 255)
			{
				banner.alpha = 0;
			}
			else
			{
				banner.alpha = 255;
			}

			if (args.length >= 2)
			{
				banner.alpha = parseInt(args[1], 0, 255);
			}

			FTBBannersNetHandler.NET.sendToAll(new MessageToggleBanner(banner.id, banner.alpha));
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}