package dev.ftb.mods.ftbbanners;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerLayerScreen;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerScreen;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;

public class FTBBannersClient extends FTBBannersCommon {
	@Override
	public void openGui(AbstractBannerEntity<?> entity, boolean sneak) {
		if (entity instanceof TextBannerEntity) {
			if (sneak || entity.layers.isEmpty()) {
				new TextBannerScreen((TextBannerEntity) entity).openGui();
			} else {
				new TextBannerLayerScreen((TextBannerEntity) entity, (BannerTextLayer) entity.layers.get(0)).openGui();
			}
		}
	}
}
