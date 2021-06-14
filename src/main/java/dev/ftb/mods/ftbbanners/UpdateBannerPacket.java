package dev.ftb.mods.ftbbanners;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftblibrary.net.snm.BaseC2SPacket;
import dev.ftb.mods.ftblibrary.net.snm.PacketID;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class UpdateBannerPacket extends BaseC2SPacket {
	private final BlockPos pos;
	private final CompoundTag nbt;

	public UpdateBannerPacket(AbstractBannerEntity<?> blockEntity) {
		pos = blockEntity.getBlockPos();
		nbt = blockEntity.write(new CompoundTag());
	}

	public UpdateBannerPacket(FriendlyByteBuf buf) {
		pos = buf.readBlockPos();
		nbt = buf.readNbt();
	}

	@Override
	public PacketID getId() {
		return FTBBanners.UPDATE_BANNER;
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeNbt(nbt);
	}

	@Override
	public void handle(NetworkManager.PacketContext context) {
		if (context.getPlayer().isCreative()) {
			BlockEntity blockEntity = context.getPlayer().level.getBlockEntity(pos);

			if (blockEntity instanceof AbstractBannerEntity) {
				((AbstractBannerEntity<?>) blockEntity).read(nbt);
				blockEntity.clearCache();
				blockEntity.setChanged();
			}
		}
	}
}
