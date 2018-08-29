package com.feed_the_beast.ftbbanners.command;

import com.feed_the_beast.ftbbanners.FTBBannersEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LatvianModder
 */
public abstract class CommandBannerBase extends CommandBase
{
	private final String name;

	public CommandBannerBase(String n)
	{
		name = n;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.banner." + name + ".usage";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return server.isSinglePlayer() || sender.canUseCommand(getRequiredPermissionLevel(), getName());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		if (args.length == 1)
		{
			List<String> list = new ArrayList<>(FTBBannersEventHandler.BANNERS.keySet());
			list.sort(null);
			return getListOfStringsMatchingLastWord(args, list);
		}

		return Collections.emptyList();
	}
}
