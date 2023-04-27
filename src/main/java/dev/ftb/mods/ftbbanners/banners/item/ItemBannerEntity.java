package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import dev.ftb.mods.ftbbanners.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class ItemBannerEntity extends AbstractBannerEntity<BannerItemLayer> {
	public boolean followPlayer = false;

	public ItemBannerEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BANNER_ITEM_TILE.get(), pos, state);
	}

	@Override
	public CompoundTag write(CompoundTag nbt) {
		super.write(nbt);
		nbt.putBoolean("follow_player", this.followPlayer);
		return nbt;
	}

	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		this.followPlayer = nbt.getBoolean("follow_player");
	}

	@Override
	public BannerItemLayer createLayer() {
		return new BannerItemLayer();
	}
}
