package dev.ftb.mods.ftbbanners.client;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerLayerScreen;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerScreen;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;

public class ClientUtil {
    public static int FULL_BRIGHT = 0x00F000F0;

    public static void openGui(AbstractBannerEntity<?> entity, boolean sneak) {
        if (entity instanceof TextBannerEntity textBanner) {
            if (sneak || entity.layers.isEmpty()) {
                new TextBannerScreen(textBanner).openGui();
            } else {
                new TextBannerLayerScreen(textBanner, (BannerTextLayer) entity.layers.get(0)).openGui();
            }
        }
    }
}
