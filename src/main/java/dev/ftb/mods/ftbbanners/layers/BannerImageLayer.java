package dev.ftb.mods.ftbbanners.layers;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class BannerImageLayer extends BannerLayer {
    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/presets/isles.png");

    public ResourceLocation image = DEFAULT_IMAGE;

    @Override
    public void write(CompoundNBT nbt) {
        nbt.putString("image", this.image.toString());
        super.write(nbt);
    }

    @Override
    public void read(CompoundNBT nbt) {
        this.image = new ResourceLocation(nbt.getString("image"));
        super.read(nbt);
    }
}
