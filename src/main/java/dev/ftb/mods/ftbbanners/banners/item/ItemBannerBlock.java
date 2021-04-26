package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ItemBannerBlock extends ImageBannerBlock {
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return FTBBanners.BANNER_ITEM_TILE.get().create();
    }
}
