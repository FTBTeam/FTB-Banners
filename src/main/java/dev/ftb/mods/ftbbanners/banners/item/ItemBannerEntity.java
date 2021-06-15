package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import net.minecraft.nbt.CompoundTag;

public class ItemBannerEntity extends AbstractBannerEntity<BannerItemLayer> {
	public boolean followPlayer = false;

	public ItemBannerEntity() {
		super(FTBBanners.BANNER_ITEM_TILE.get());
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
