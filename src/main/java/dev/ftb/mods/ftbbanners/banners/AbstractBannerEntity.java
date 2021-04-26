package dev.ftb.mods.ftbbanners.banners;

import dev.ftb.mods.ftbbanners.layers.BannerLayer;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public abstract class AbstractBannerEntity<T extends BannerLayer> extends TileEntity {
    public float rotationX = 0F;
    public float rotationY = 0F;
    public float rotationZ = 0F;
    public float offsetX = 0F;
    public float offsetY = 0F;
    public float offsetZ = 0F;
    public float scale = 1F;

    public AbstractBannerEntity(TileEntityType<?> type) {
        super(type);
    }

    public CompoundNBT write(CompoundNBT nbt) {
        ListNBT layerListTag = new ListNBT();
        for (T layer : this.getLayers()) {
            CompoundNBT nbt1 = new CompoundNBT();
            layer.write(nbt1);
            layerListTag.add(nbt1);
        }

        nbt.put("layers", layerListTag);

        nbt.putFloat("rotation_x", this.rotationX);
        nbt.putFloat("rotation_y", this.rotationY);
        nbt.putFloat("rotation_z", this.rotationZ);
        nbt.putFloat("offset_x", this.offsetX);
        nbt.putFloat("offset_y", this.offsetY);
        nbt.putFloat("offset_z", this.offsetZ);
        nbt.putFloat("scale", this.scale);

        return nbt;
    }

    public void read(CompoundNBT nbt) {
        ArrayList<T> layerList = new ArrayList<>();
        ListNBT layerListTag = nbt.getList("layers", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < layerListTag.size(); i++) {
            CompoundNBT nbt1 = layerListTag.getCompound(i);
            T layer = this.createLayer();
            layer.read(nbt1);
            layerList.add(layer);
        }

        this.setLayers(layerList);

        this.rotationX = nbt.getFloat("rotation_x");
        this.rotationY = nbt.getFloat("rotation_y");

        if (this.rotationY == 0F) {
            this.rotationY = nbt.getFloat("rotation");
        }

        this.rotationZ = nbt.getFloat("rotation_z");
        this.offsetX = nbt.getFloat("offset_x");
        this.offsetY = nbt.getFloat("offset_y");
        this.offsetZ = nbt.getFloat("offset_z");
        this.scale = nbt.getFloat("scale");
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

    public abstract T[] getLayers();

    public abstract void setLayers(ArrayList<T> layers);

    public abstract T createLayer();
}
