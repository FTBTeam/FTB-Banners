package dev.ftb.mods.ftbbanners;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerScreen;

public class FTBBannersClient extends FTBBannersCommon {
	@Override
	public void openGui(AbstractBannerEntity<?> entity) {
		if (entity instanceof TextBannerEntity) {
			new TextBannerScreen((TextBannerEntity) entity).openGui();
		}
	}
}
