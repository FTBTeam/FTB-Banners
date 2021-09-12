package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class ItemBannerBlock extends ImageBannerBlock {
	@Nullable
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return FTBBanners.BANNER_ITEM_TILE.get().create();
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult trace) {
		if (!playerEntity.isCreative()) {
			return InteractionResult.PASS;
		}

		ItemStack itemInHand = playerEntity.getItemInHand(hand);

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ItemBannerEntity) {
			for (BannerItemLayer layer : ((ItemBannerEntity) blockEntity).layers) {
				if (!layer.isVisible(playerEntity)) {
					continue;
				}

				if (itemInHand.isEmpty() && playerEntity.isCrouching() && layer.items.size() > 0) {
					layer.items.remove(layer.items.size() - 1);
					blockEntity.setChanged();
					continue;
				}

				if (!itemInHand.isEmpty()) {
					layer.items.add(itemInHand.copy());
					blockEntity.setChanged();
				}
			}

			return InteractionResult.CONSUME;
		}

		return super.use(state, world, pos, playerEntity, hand, trace);
	}
}
