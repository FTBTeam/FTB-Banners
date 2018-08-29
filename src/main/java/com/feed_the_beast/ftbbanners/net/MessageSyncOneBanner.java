package com.feed_the_beast.ftbbanners.net;

import com.feed_the_beast.ftbbanners.Banner;
import com.feed_the_beast.ftbbanners.FTBBannersClientEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author LatvianModder
 */
public class MessageSyncOneBanner implements IMessage, IMessageHandler<MessageSyncOneBanner, IMessage>
{
	private Banner banner;

	public MessageSyncOneBanner()
	{
	}

	public MessageSyncOneBanner(Banner b)
	{
		banner = b;
	}

	@Override
	public void toBytes(ByteBuf data)
	{
		ByteBufUtils.writeUTF8String(data, banner.id);
		banner.write(data);
	}

	@Override
	public void fromBytes(ByteBuf data)
	{
		banner = new Banner(ByteBufUtils.readUTF8String(data));
		banner.read(data);
	}

	@Override
	public IMessage onMessage(MessageSyncOneBanner message, MessageContext ctx)
	{
		onMessage(message.banner);
		return null;
	}

	private void onMessage(Banner banner)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (banner.height == 0)
			{
				FTBBannersClientEventHandler.BANNERS.remove(banner.id);
			}
			else
			{
				FTBBannersClientEventHandler.BANNERS.put(banner.id, banner);
			}
		});
	}
}