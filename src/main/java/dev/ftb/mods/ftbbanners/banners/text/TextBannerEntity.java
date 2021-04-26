package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public class TextBannerEntity extends AbstractBannerEntity<BannerTextLayer> {
    public float scale = 1F;
    BannerTextLayer[] layers = {new BannerTextLayer()};

    public TextBannerEntity() {
        super(FTBBanners.BANNER_TEXT_TILE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putFloat("scale", this.scale);
        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        this.scale = nbt.getFloat("scale");
    }

    @Override
    public BannerTextLayer[] getLayers() {
        return this.layers;
    }

    @Override
    public void setLayers(ArrayList<BannerTextLayer> layers) {
        this.layers = layers.toArray(new BannerTextLayer[0]);
    }

    @Override
    public BannerTextLayer createLayer() {
        return new BannerTextLayer();
    }
}
