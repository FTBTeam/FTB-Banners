package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import dev.ftb.mods.ftbbanners.net.OpenBannerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextBannerBlock extends ImageBannerBlock implements EntityBlock {
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TextBannerEntity(pos, state);
	}

	@Override
	@Deprecated
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult trace) {
		if (!entity.isCreative()) {
			return InteractionResult.PASS;
		}

		BlockEntity banner = world.getBlockEntity(pos);
		if (!(banner instanceof TextBannerEntity textBanner)) {
			return InteractionResult.PASS;
		}

		ItemStack held = entity.getItemInHand(hand);

		if (!(held.getItem() instanceof WritableBookItem) && !(held.getItem() instanceof WrittenBookItem)) {
			if (!world.isClientSide()) {
				new OpenBannerPacket(textBanner, entity.isCrouching()).sendTo((ServerPlayer) entity);
			}

			return InteractionResult.SUCCESS;
		}

		CompoundTag bookData = held.getOrCreateTag();
		if (bookData.contains("pages")) {
			ListTag pages = bookData.getList("pages", Tag.TAG_STRING);
			if (pages.size() > 0) {
				textBanner.layers.get(0).text = new ArrayList<>(Arrays.asList(
						pages.stream()
								.map(Tag::getAsString)
								.collect(Collectors.joining("\n"))
								.split("\n")
				));
				textBanner.clearCache();
				banner.setChanged();
			}
		}

		return InteractionResult.CONSUME;
	}
}
