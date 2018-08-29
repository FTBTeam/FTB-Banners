package com.feed_the_beast.ftbbanners.net;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersClientEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LatvianModder
 */
public class MessageSyncBanners implements IMessage, IMessageHandler<MessageSyncBanners, IMessage>
{
	private List<Banner> banners;

	public MessageSyncBanners()
	{
	}

	public MessageSyncBanners(Collection<Banner> b)
	{
		banners = new ArrayList<>(b);
	}

	@Override
	public void toBytes(ByteBuf data)
	{
		data.writeShort(banners.size());

		for (Banner banner : banners)
		{
			ByteBufUtils.writeUTF8String(data, banner.id);
			banner.write(data);
		}
	}

	@Override
	public void fromBytes(ByteBuf data)
	{
		int s = data.readUnsignedShort();
		banners = new ArrayList<>(s);

		for (int i = 0; i < s; i++)
		{
			Banner banner = new Banner(ByteBufUtils.readUTF8String(data));
			banner.read(data);
			banners.add(banner);
		}
	}

	@Override
	public IMessage onMessage(MessageSyncBanners message, MessageContext ctx)
	{
		onMessage(message);
		return null;
	}

	private void onMessage(MessageSyncBanners message)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			FTBBannersClientEventHandler.BANNERS.clear();

			for (Banner banner : message.banners)
			{
				FTBBannersClientEventHandler.BANNERS.put(banner.id, banner);
			}
		});
	}
}