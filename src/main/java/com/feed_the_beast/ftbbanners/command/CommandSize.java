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
			banner.width = (float) parseDouble(args[1], 0.01D, 256D);
			banner.height = (float) parseDouble(args[2], 0.01D, 256D);
			banner.saveAndSend(sender.getEntityWorld());
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}