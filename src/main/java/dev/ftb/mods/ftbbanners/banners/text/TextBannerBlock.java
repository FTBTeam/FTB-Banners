package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class TextBannerBlock extends ImageBannerBlock {
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return FTBBanners.BANNER_TEXT_TILE.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult trace) {
        ItemStack held = entity.getItemInHand(hand);

        if (!(held.getItem() instanceof WritableBookItem) && !(held.getItem() instanceof WrittenBookItem)) {
            return super.use(state, world, pos, entity, hand, trace);
        }

        TileEntity banner = world.getBlockEntity(pos);
        if (!(banner instanceof TextBannerEntity)) {
            return super.use(state, world, pos, entity, hand, trace);
        }

        CompoundNBT bookData = held.getOrCreateTag();
        if (bookData.contains("pages")) {
            ListNBT pages = bookData.getList("pages", Constants.NBT.TAG_STRING);
            if (pages.size() > 0) {
                ((TextBannerEntity) banner).layers[0].text = pages.stream().map(INBT::getAsString).collect(Collectors.joining("\n"));
                banner.setChanged();
            }
        }

        return ActionResultType.CONSUME;
    }
}
