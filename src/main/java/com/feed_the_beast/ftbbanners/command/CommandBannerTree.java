package com.feed_the_beast.ftbbanners.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

/**
 * @author LatvianModder
 */
public class CommandBannerTree extends CommandTreeBase
{
	public CommandBannerTree()
	{
		addSubcommand(new CommandCreate("create"));
		addSubcommand(new CommandDelete("delete"));
		addSubcommand(new CommandList("list"));
		addSubcommand(new CommandSet("set"));
		addSubcommand(new CommandRotate("rotate"));
		addSubcommand(new CommandMove("move"));
		addSubcommand(new CommandWind("wind"));
		addSubcommand(new CommandSize("size"));
		addSubcommand(new CommandImage("image"));
	}

	@Override
	public String getName()
	{
		return "banner";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.banner.usage";
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
	{
	}
}