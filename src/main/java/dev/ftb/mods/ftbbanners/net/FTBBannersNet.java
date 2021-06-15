package dev.ftb.mods.ftbbanners.net;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftblibrary.net.snm.PacketID;
import dev.ftb.mods.ftblibrary.net.snm.SimpleNetworkManager;

public interface FTBBannersNet {
	SimpleNetworkManager NET = SimpleNetworkManager.create(FTBBanners.MOD_ID);

	PacketID UPDATE_BANNER = NET.registerC2S("update_banner", UpdateBannerPacket::new);
	PacketID OPEN_BANNER = NET.registerS2C("open_text_banner", OpenBannerPacket::new);

	static void init() {

	}
}
