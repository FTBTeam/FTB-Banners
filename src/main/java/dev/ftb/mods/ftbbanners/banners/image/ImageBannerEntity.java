package dev.ftb.mods.ftbbanners.banners.image;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerImageLayer;
import net.minecraft.nbt.CompoundTag;

/**
 * @author LatvianModder
 */
public class ImageBannerEntity extends AbstractBannerEntity<BannerImageLayer> {
	public float width = 1F;
	public float height = 1F;
	public int alpha = 255;
	public float wind = 0.1F;
	public float windSpeed = 1F;
	public boolean hover = false;

	public ImageBannerEntity() {
		super(FTBBanners.BANNER_IMAGE_TILE.get());
	}

	@Override
	public CompoundTag write(CompoundTag nbt) {
		super.write(nbt);
		nbt.putFloat("width", this.width);
		nbt.putFloat("height", this.height);
		nbt.putInt("alpha", this.alpha);
		nbt.putFloat("wind", this.wind);
		nbt.putFloat("wind_speed", this.windSpeed);
		nbt.putBoolean("hover", this.hover);
		return nbt;
	}

	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		this.width = nbt.getFloat("width");
		this.height = nbt.getFloat("height");
		this.alpha = nbt.getInt("alpha");
		this.wind = nbt.getFloat("wind");
		if (nbt.contains("wind_speed")) {
			this.windSpeed = nbt.getFloat("wind_speed");
		}

		this.hover = nbt.getBoolean("hover");
	}

	@Override
	public BannerImageLayer createLayer() {
		return new BannerImageLayer();
	}
}
