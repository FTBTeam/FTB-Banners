package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import net.minecraft.nbt.CompoundTag;

public class TextBannerEntity extends AbstractBannerEntity<BannerTextLayer> {
	public float scale = 1F;
	public boolean followPlayer = false;

	public TextBannerEntity() {
		super(FTBBanners.BANNER_TEXT_TILE.get());
	}

	@Override
	public CompoundTag write(CompoundTag nbt) {
		super.write(nbt);
		nbt.putFloat("scale", this.scale);
		nbt.putBoolean("follow_player", this.followPlayer);
		return nbt;
	}

	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		this.scale = nbt.getFloat("scale");
		this.followPlayer = nbt.getBoolean("follow_player");
	}

	@Override
	public BannerTextLayer createLayer() {
		return new BannerTextLayer();
	}
}
