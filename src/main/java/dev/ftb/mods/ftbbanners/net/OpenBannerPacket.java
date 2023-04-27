package dev.ftb.mods.ftbbanners.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.AbstractBannerEntity;
import dev.ftb.mods.ftbbanners.client.ClientUtil;
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

		if (blockEntity instanceof AbstractBannerEntity<?> banner) {
			banner.read(nbt);
			banner.clearCache();
			ClientUtil.openGui(banner, sneak);
		}
	}
}
