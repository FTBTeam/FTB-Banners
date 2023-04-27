package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import dev.ftb.mods.ftbbanners.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TextBannerEntity extends AbstractBannerEntity<BannerTextLayer> {
	public float scale = 1F;
	public boolean followPlayer = false;

	public TextBannerEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BANNER_TEXT_TILE.get(), pos, state);
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
