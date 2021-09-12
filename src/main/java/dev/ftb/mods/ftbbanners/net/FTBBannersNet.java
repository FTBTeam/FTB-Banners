package dev.ftb.mods.ftbbanners.net;

import dev.ftb.mods.ftbbanners.FTBBanners;
import me.shedaniel.architectury.networking.simple.MessageType;
import me.shedaniel.architectury.networking.simple.SimpleNetworkManager;

public interface FTBBannersNet {
	SimpleNetworkManager NET = SimpleNetworkManager.create(FTBBanners.MOD_ID);

	MessageType UPDATE_BANNER = NET.registerC2S("update_banner", UpdateBannerPacket::new);
	MessageType OPEN_BANNER = NET.registerS2C("open_text_banner", OpenBannerPacket::new);

	static void init() {
	}
}
