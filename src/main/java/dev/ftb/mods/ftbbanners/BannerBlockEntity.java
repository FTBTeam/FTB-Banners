package dev.ftb.mods.ftbbanners;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

/**
 * @author LatvianModder
 */
public class BannerBlockEntity extends TileEntity {
    public BannerLayer[] layers = {new BannerLayer()};
    public float width = 1F;
    public float height = 1F;
    public float rotationX = 0F;
    public float rotationY = 0F;
    public float rotationZ = 0F;
    public int alpha = 255;
    public float wind = 0.1F;
    public float windSpeed = 1F;
    public float offsetX = 0F;
    public float offsetY = 0F;
    public float offsetZ = 0F;
    public boolean hover = false;

    public BannerBlockEntity() {
        super(FTBBanners.BANNER_TILE.get());
    }

    public CompoundNBT write(CompoundNBT nbt) {
        ListNBT layerListTag = new ListNBT();

        for (BannerLayer layer : this.layers) {
            CompoundNBT nbt1 = new CompoundNBT();
            nbt1.putString("image", layer.image.toString());
            nbt1.putBoolean("glow", layer.glow);
            nbt1.putBoolean("culling", layer.culling);
            nbt1.putString("text", layer.text);
            nbt1.putString("textAlign", layer.textAlign);
            nbt1.putString("textBackground", layer.textBackground);
            nbt1.putFloat("textScale", layer.textScale);


            if (!layer.gameStage.isEmpty() || ModList.get().isLoaded("gamestages")) {
                nbt1.putString("game_stage", layer.gameStage);
            }

            layerListTag.add(nbt1);
        }

        nbt.put("layers", layerListTag);
        nbt.putFloat("width", this.width);
        nbt.putFloat("height", this.height);
        nbt.putFloat("rotation_x", this.rotationX);
        nbt.putFloat("rotation_y", this.rotationY);
        nbt.putFloat("rotation_z", this.rotationZ);
        nbt.putInt("alpha", this.alpha);
        nbt.putFloat("wind", this.wind);
        nbt.putFloat("wind_speed", this.windSpeed);
        nbt.putFloat("offset_x", this.offsetX);
        nbt.putFloat("offset_y", this.offsetY);
        nbt.putFloat("offset_z", this.offsetZ);
        nbt.putBoolean("hover", this.hover);
        return nbt;
    }

    public void read(CompoundNBT nbt) {
        ArrayList<BannerLayer> layerList = new ArrayList<>();
        ListNBT layerListTag = nbt.getList("layers", Constants.NBT.TAG_COMPOUND);

        if (layerListTag.isEmpty()) {
            String img = nbt.getString("image");

            if (!img.isEmpty()) {
                BannerLayer layer = new BannerLayer();
                layer.image = new ResourceLocation(img);
                layer.glow = nbt.getBoolean("glow");
                layer.culling = nbt.getBoolean("culling");
                layer.gameStage = nbt.getString("game_stage");
                layerList.add(layer);
            }
        } else {
            for (int i = 0; i < layerListTag.size(); i++) {
                CompoundNBT nbt1 = layerListTag.getCompound(i);
                BannerLayer layer = new BannerLayer();
                layer.image = new ResourceLocation(nbt1.getString("image"));
                layer.glow = nbt1.getBoolean("glow");
                layer.culling = nbt1.getBoolean("culling");
                layer.gameStage = nbt1.getString("game_stage");

                layer.text = nbt1.getString("text");
                layer.textAlign = nbt1.getString("textAlign");
                layer.textBackground = nbt1.getString("textBackground");
                layer.textScale = nbt1.getFloat("textScale");

                layerList.add(layer);
            }
        }

        this.layers = layerList.toArray(new BannerLayer[0]);
        this.width = nbt.getFloat("width");
        this.height = nbt.getFloat("height");

        this.rotationX = nbt.getFloat("rotation_x");
        this.rotationY = nbt.getFloat("rotation_y");

        if (this.rotationY == 0F) {
            this.rotationY = nbt.getFloat("rotation");
        }

        this.rotationZ = nbt.getFloat("rotation_z");

        this.alpha = nbt.getInt("alpha");
        this.wind = nbt.getFloat("wind");

        if (nbt.contains("wind_speed")) {
            this.windSpeed = nbt.getFloat("wind_speed");
        }

        this.offsetX = nbt.getFloat("offset_x");
        this.offsetY = nbt.getFloat("offset_y");
        this.offsetZ = nbt.getFloat("offset_z");
        this.hover = nbt.getBoolean("hover");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        this.write(compound);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.read(compound);
    }


    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 0, this.write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getTag());
    }

    @Override
    public double getViewDistance() {
        return 128D * 128D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
