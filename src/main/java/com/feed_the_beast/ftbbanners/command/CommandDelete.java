package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

/**
 * @author LatvianModder
 */
public class CommandDelete extends CommandBannerBase
{
	public CommandDelete(String n)
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
			FTBBannersEventHandler.BANNERS.remove(banner.id);
			banner.width = 0;
			banner.height = 0;
			banner.alpha = 0;
			banner.saveAndSend();
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}