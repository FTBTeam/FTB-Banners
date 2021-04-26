package dev.ftb.mods.ftbbanners.banners.item;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public class ItemBannerEntity extends AbstractBannerEntity<BannerItemLayer> {
    public boolean followPlayer = false;
    private BannerItemLayer[] layers = {new BannerItemLayer()};

    public ItemBannerEntity() {
        super(FTBBanners.BANNER_ITEM_TILE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putBoolean("follow_player", this.followPlayer);
        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        this.followPlayer = nbt.getBoolean("follow_player");
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
