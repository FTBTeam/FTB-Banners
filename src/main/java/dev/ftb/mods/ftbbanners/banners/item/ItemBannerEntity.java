package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;

import java.util.ArrayList;

public class ItemBannerEntity extends AbstractBannerEntity<BannerItemLayer> {
    private BannerItemLayer[] layers = {new BannerItemLayer()};

    public ItemBannerEntity() {
        super(FTBBanners.BANNER_IMAGE_TILE.get());
    }

    @Override
    public BannerItemLayer[] getLayers() {
        return this.layers;
    }

    @Override
    public void setLayers(ArrayList<BannerItemLayer> layers) {
        this.layers = layers.toArray(new BannerItemLayer[0]);
    }

    @Override
    public BannerItemLayer createLayer() {
        return new BannerItemLayer();
    }
}
