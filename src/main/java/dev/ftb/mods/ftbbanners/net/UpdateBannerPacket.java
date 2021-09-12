package dev.ftb.mods.ftbbanners.net;

import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import me.shedaniel.architectury.networking.NetworkManager;
import me.shedaniel.architectury.networking.simple.BaseC2SMessage;
import me.shedaniel.architectury.networking.simple.MessageType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class UpdateBannerPacket extends BaseC2SMessage {
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
	public MessageType getType() {
		return FTBBannersNet.UPDATE_BANNER;
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
