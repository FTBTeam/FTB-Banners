package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;

/**
 * @author LatvianModder
 */
public class CommandCreate extends CommandBannerBase
{
	public CommandCreate(String n)
	{
		super(n);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
	{
		if (!FTBBannersEventHandler.BANNERS.containsKey(args[0]))
		{
			Banner banner = new Banner(args[0]);
			FTBBannersEventHandler.BANNERS.put(banner.id, banner);

			banner.dimension = sender.getEntityWorld().provider.getDimension();
			Vec3d pos = sender.getPositionVector();
			banner.x = pos.x;
			banner.y = pos.y;
			banner.z = pos.z;
			banner.saveAndSend();
		}
	}
}