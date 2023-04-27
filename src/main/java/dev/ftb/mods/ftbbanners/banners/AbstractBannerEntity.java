package dev.ftb.mods.ftbbanners.banners;

import dev.ftb.mods.ftbbanners.layers.BannerLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBannerEntity<T extends BannerLayer> extends BlockEntity {
	public float rotationX = 0F;
	public float rotationY = 0F;
	public float rotationZ = 0F;
	public float offsetX = 0F;
	public float offsetY = 0F;
	public float offsetZ = 0F;
	public float scale = 1F;
	public List<T> layers = new ArrayList<>(1);

	public AbstractBannerEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);

		layers.add(createLayer());
	}

	public CompoundTag write(CompoundTag nbt) {
		ListTag layerListTag = new ListTag();
		for (T layer : layers) {
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
		ListTag layerListTag = nbt.getList("layers", Tag.TAG_COMPOUND);
		layers = new ArrayList<>(layerListTag.size());

		for (int i = 0; i < layerListTag.size(); i++) {
			CompoundTag nbt1 = layerListTag.getCompound(i);
			T layer = this.createLayer();
			layer.read(nbt1);
			layers.add(layer);
		}

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
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		this.write(tag);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.read(compound);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		saveAdditional(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		read(tag);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		if (pkt.getTag() != null) {
			this.read(pkt.getTag());
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public abstract T createLayer();

	public void clearCache() {
		layers.forEach(BannerLayer::clearCache);
	}
}
