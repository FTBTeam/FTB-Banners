package dev.ftb.mods.ftbbanners.banners;

import dev.ftb.mods.ftbbanners.layers.BannerLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public abstract class AbstractBannerEntity<T extends BannerLayer> extends BlockEntity {
	public float rotationX = 0F;
	public float rotationY = 0F;
	public float rotationZ = 0F;
	public float offsetX = 0F;
	public float offsetY = 0F;
	public float offsetZ = 0F;
	public float scale = 1F;

	public AbstractBannerEntity(BlockEntityType<?> type) {
		super(type);
	}

	public CompoundTag write(CompoundTag nbt) {
		ListTag layerListTag = new ListTag();
		for (T layer : this.getLayers()) {
			CompoundTag nbt1 = new CompoundTag();
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

	public void read(CompoundTag nbt) {
		ArrayList<T> layerList = new ArrayList<>();
		ListTag layerListTag = nbt.getList("layers", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < layerListTag.size(); i++) {
			CompoundTag nbt1 = layerListTag.getCompound(i);
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
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		this.write(compound);
		return compound;
	}

	@Override
	public void load(BlockState state, CompoundTag compound) {
		super.load(state, compound);
		this.read(compound);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundTag tag) {
		this.read(tag);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 0, this.write(new CompoundTag()));
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		this.read(pkt.getTag());
	}

	@Override
	public double getViewDistance() {
		return 128D * 128D;
	}

	@Override
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public abstract T[] getLayers();

	public abstract void setLayers(ArrayList<T> layers);

	public abstract T createLayer();
}
