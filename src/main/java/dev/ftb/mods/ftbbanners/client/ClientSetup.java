package dev.ftb.mods.ftbbanners.client;

import dev.ftb.mods.ftbbanners.banners.image.ImageBannerRenderer;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerRenderer;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerRenderer;
import dev.ftb.mods.ftbbanners.register.ModBlockEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetup {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::registerRenderers);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BANNER_IMAGE_TILE.get(), ImageBannerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BANNER_ITEM_TILE.get(), ItemBannerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BANNER_TEXT_TILE.get(), TextBannerRenderer::new);
    }
}
