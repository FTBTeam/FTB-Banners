package dev.ftb.mods.ftbbanners.layers;

import dev.ftb.mods.ftbbanners.banners.text.CachedText;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BannerTextLayer extends BannerLayer {
	public String[] text = new String[]{"&6This is &ltest&r text to &cshow how &athe", "Lines and&ksuch work&r&o &nwith&r the\nBanner when rendering text"};
	public boolean textShadow = false;
	public String alignment = "left";
	public String bgColor = "";
	public float bgAlpha = 1F;
	public Object cached = null;

	@Override
	public void write(CompoundTag nbt) {
		super.write(nbt);
		nbt.putString("text", String.join("\n", text));
		nbt.putBoolean("text_shadow", this.textShadow);
		nbt.putString("align", this.alignment);
		nbt.putString("bg_color", this.bgColor);
		nbt.putFloat("bg_alpha", this.bgAlpha);
	}

	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		this.text = nbt.getString("text").split("\n");
		cached = null;
		this.textShadow = nbt.getBoolean("text_shadow");
		this.alignment = nbt.getString("align");
		this.bgColor = nbt.getString("bg_color");
		this.bgAlpha = nbt.getFloat("bg_alpha");
	}

	@Override
	public boolean isVisible(Player player) {
		return text.length > 0 && super.isVisible(player);
	}

	@OnlyIn(Dist.CLIENT)
	public CachedText getText() {
		if (cached == null) {
			cached = new CachedText(this, text);
		}

		return (CachedText) cached;
	}
}
