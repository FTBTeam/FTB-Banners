package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemBannerBlock extends ImageBannerBlock {
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return FTBBanners.BANNER_ITEM_TILE.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult trace) {
        if (!playerEntity.isCreative()) {
            return ActionResultType.PASS;
        }

        ItemStack itemInHand = playerEntity.getItemInHand(hand);

        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemBannerEntity) {
            BannerItemLayer[] layers = ((ItemBannerEntity) blockEntity).getLayers();
            for (BannerItemLayer layer : layers) {
                if (!layer.isVisible(playerEntity)) {
                    continue;
                }

                if (itemInHand.isEmpty() && playerEntity.isCrouching() && layer.items.size() > 0) {
                    layer.items.remove(layer.items.size() - 1);
                    blockEntity.setChanged();
                    continue;
                }

                if (!itemInHand.isEmpty()) {
                    layer.items.add(itemInHand);
                    blockEntity.setChanged();
                }
            }

            return ActionResultType.CONSUME;
        }

        return super.use(state, world, pos, playerEntity, hand, trace);
    }
}
