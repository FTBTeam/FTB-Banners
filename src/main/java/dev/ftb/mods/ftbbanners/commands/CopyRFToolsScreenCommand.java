package dev.ftb.mods.ftbbanners.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.stream.Collectors;

public class CopyRFToolsScreenCommand {

	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("copy-rf-screen-text")
				.requires(cs -> cs.hasPermission(2))
				.executes(CopyRFToolsScreenCommand::copyScreenText);
	}

	private static int copyScreenText(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer player = ctx.getSource().getPlayerOrException();
		HitResult pick = player.pick(20, 0, false);

		if (!(pick instanceof BlockHitResult blockTrace)) {
			ctx.getSource().sendSuccess(Component.literal("No RF Screen block found"), false);
			return 0;
		}

		BlockEntity blockEntity = player.level.getBlockEntity(blockTrace.getBlockPos());
		if (blockEntity == null) {
			ctx.getSource().sendSuccess(Component.literal("No RF Screen block found"), false);
			return 0;
		}

		CompoundTag tileData = blockEntity.saveWithId();
		if (!tileData.contains("id")
				&& !tileData.getString("id").equals("rftoolsutility:creative_screen")
				&& !tileData.getString("id").equals("rftoolsutility:screen")) {
			ctx.getSource().sendSuccess(Component.literal("Tile is not a RF Screen"), false);
			return 0;
		}

		if (!tileData.contains("Items")) {
			ctx.getSource().sendSuccess(Component.literal("Invalid screen tile"), false);
			return 0;
		}

		String screenText = tileData.getList("Items", Tag.TAG_COMPOUND).stream()
				.filter(e -> ((CompoundTag) e).getCompound("tag").contains("text"))
				.map(e -> ((CompoundTag) e).getCompound("tag").getString("text"))
				.collect(Collectors.joining("\n"));

		ItemStack stack = new ItemStack(Items.WRITABLE_BOOK, 1);
		CompoundTag tag = stack.getOrCreateTag();
		ListTag nbt = new ListTag();
		if (screenText.length() > 266) {
			for (int i = 0; i <= (screenText.length() / 266); i++) {
				nbt.add(StringTag.valueOf(screenText.substring((266 * i), Math.min((266 * i) + 266, screenText.length()))));
			}
		} else {
			nbt.add(StringTag.valueOf(screenText));
		}

		tag.put("pages", nbt);

		Block.popResource(player.level, blockTrace.getBlockPos(), stack);

		return 0;
	}
}
