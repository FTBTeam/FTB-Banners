package dev.ftb.mods.ftbbanners.net;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import me.shedaniel.architectury.networking.NetworkManager;
import me.shedaniel.architectury.networking.simple.BaseS2CMessage;
import me.shedaniel.architectury.networking.simple.MessageType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class OpenBannerPacket extends BaseS2CMessage {
	private final BlockPos pos;
	private final CompoundTag nbt;
	private final boolean sneak;

	public OpenBannerPacket(AbstractBannerEntity<?> blockEntity, boolean s) {
		pos = blockEntity.getBlockPos();
		nbt = blockEntity.write(new CompoundTag());
		sneak = s;
	}

	public OpenBannerPacket(FriendlyByteBuf buf) {
		pos = buf.readBlockPos();
		nbt = buf.readNbt();
		sneak = buf.readBoolean();
	}

	@Override
	public MessageType getType() {
		return FTBBannersNet.OPEN_BANNER;
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeNbt(nbt);
		buf.writeBoolean(sneak);
	}

	@Override
	public void handle(NetworkManager.PacketContext context) {
		BlockEntity blockEntity = context.getPlayer().level.getBlockEntity(pos);

		if (blockEntity instanceof AbstractBannerEntity) {
			((AbstractBannerEntity<?>) blockEntity).read(nbt);
			blockEntity.clearCache();
			FTBBanners.PROXY.openGui((AbstractBannerEntity<?>) blockEntity, sneak);
		}
	}
}
