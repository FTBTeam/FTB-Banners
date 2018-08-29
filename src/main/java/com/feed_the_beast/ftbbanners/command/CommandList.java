package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author LatvianModder
 */
public class CommandList extends CommandBannerBase
{
	public CommandList(String n)
	{
		super(n);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		return Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
	{
		for (Banner banner : FTBBannersEventHandler.BANNERS.values())
		{
			sender.sendMessage(new TextComponentString(banner.id));
			sender.sendMessage(new TextComponentString(String.format(" - Image: %s", banner.image)));
			sender.sendMessage(new TextComponentString(String.format(" - Pos: %.2f %.2f %.2f", banner.x, banner.y, banner.z)));
			sender.sendMessage(new TextComponentString(String.format(" - Size: %d x %d", banner.width, banner.height)));
			sender.sendMessage(new TextComponentString(String.format(" - Rotation: %d", (int) banner.rotation)));
		}
	}
}