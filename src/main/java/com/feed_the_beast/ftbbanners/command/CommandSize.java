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
public class CommandSize extends CommandBannerBase
{
	public CommandSize(String n)
	{
		super(n);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 3)
		{
			throw new WrongUsageException(getUsage(sender));
		}

		Banner banner = FTBBannersEventHandler.BANNERS.get(args[0]);

		if (banner != null)
		{
			banner.width = parseInt(args[1], 1, 255);
			banner.height = parseInt(args[2], 1, 255);
			banner.saveAndSend(sender.getEntityWorld());
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}