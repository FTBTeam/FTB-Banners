package dev.ftb.mods.ftbbanners.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants;

import java.util.stream.Collectors;

public class CopyRFToolsScreenCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("copy-rf-screen-text")
            .requires(cs -> cs.hasPermission(2))
            .executes(CopyRFToolsScreenCommand::copyScreenText);
    }

    private static int copyScreenText(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrException();
        RayTraceResult pick = player.pick(20, 0, false);

        if (pick.getType() != RayTraceResult.Type.BLOCK) {
            ctx.getSource().sendSuccess(new StringTextComponent("No RF Screen block found"), false);
            return 0;
        }

        BlockRayTraceResult blockTrace = (BlockRayTraceResult) pick;
        TileEntity blockEntity = player.level.getBlockEntity(blockTrace.getBlockPos());

        if (blockEntity == null) {
            ctx.getSource().sendSuccess(new StringTextComponent("No RF Screen block found"), false);
            return 0;
        }

        CompoundNBT tileData = blockEntity.save(new CompoundNBT());
        if (!tileData.contains("id") && !tileData.getString("id").equals("rftoolsutility:creative_screen") && !tileData.getString("id").equals("rftoolsutility:screen")) {
            ctx.getSource().sendSuccess(new StringTextComponent("Tile is not a RF Screen"), false);
            return 0;
        }

        if (!tileData.contains("Items")) {
            ctx.getSource().sendSuccess(new StringTextComponent("Invalid screen tile"), false);
            return 0;
        }

        String screenText = tileData.getList("Items", Constants.NBT.TAG_COMPOUND).stream()
            .filter(e -> ((CompoundNBT) e).getCompound("tag").contains("text"))
            .map(e -> ((CompoundNBT) e).getCompound("tag").getString("text"))
            .collect(Collectors.joining("\n"));

        ItemStack stack = new ItemStack(Items.WRITABLE_BOOK, 1);
        CompoundNBT tag = stack.getOrCreateTag();
        ListNBT nbt = new ListNBT();
        if (screenText.length() > 266) {
            for (int i = 0; i <= (screenText.length() / 266); i++) {
                nbt.add(StringNBT.valueOf(screenText.substring((266 * i), Math.min((266 * i) + 266, screenText.length()))));
            }
        } else {
            nbt.add(StringNBT.valueOf(screenText));
        }

        tag.put("pages", nbt);

        InventoryHelper.dropItemStack(player.level, blockTrace.getBlockPos().getX(), blockTrace.getBlockPos().getY(), blockTrace.getBlockPos().getZ(), stack);

        return 0;
    }
}
