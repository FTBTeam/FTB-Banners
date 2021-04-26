package dev.ftb.mods.ftbbanners.layers;

import net.minecraft.nbt.CompoundNBT;

public class BannerTextLayer extends BannerLayer {
    public String text = "";
    public boolean textShadow = false;
    public String alignment = "left";
    public String bgColor = "";
    public float bgAlpha = 1f;

    @Override
    public void write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putString("text", this.text);
        nbt.putBoolean("text_shadow", this.textShadow);
        nbt.putString("align", this.alignment);
        nbt.putString("bg_color", this.bgColor);
        nbt.putFloat("bg_alpha", this.bgAlpha);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        this.text = nbt.getString("text");
        this.textShadow = nbt.getBoolean("text_shadow");
        this.alignment = nbt.getString("align");
        this.bgColor = nbt.getString("bg_color");
        this.bgAlpha = nbt.getFloat("bg_alpha");
    }
}
