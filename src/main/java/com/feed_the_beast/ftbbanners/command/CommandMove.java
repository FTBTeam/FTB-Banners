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
public class CommandMove extends CommandBannerBase
{
	public CommandMove(String n)
	{
		super(n);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 5)
		{
			throw new WrongUsageException(getUsage(sender));
		}

		Banner banner = FTBBannersEventHandler.BANNERS.get(args[0]);

		if (banner != null)
		{
			banner.dimension = parseInt(args[1]);
			banner.x = parseDouble(args[2]);
			banner.y = parseDouble(args[3]);
			banner.z = parseDouble(args[4]);
			banner.saveAndSend();
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}