package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

/**
 * @author LatvianModder
 */
public class CommandImage extends CommandBannerBase
{
	public CommandImage(String n)
	{
		super(n);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 2)
		{
			throw new WrongUsageException(getUsage(sender));
		}

		Banner banner = FTBBannersEventHandler.BANNERS.get(args[0]);

		if (banner != null)
		{
			banner.image = new ResourceLocation(args[1]);
			banner.saveAndSend(sender.getEntityWorld());
		}
		else
		{
			throw new WrongUsageException(getUsage(sender));
		}
	}
}