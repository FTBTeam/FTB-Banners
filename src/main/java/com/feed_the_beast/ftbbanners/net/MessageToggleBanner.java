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
public class MessageToggleBanner implements IMessage, IMessageHandler<MessageToggleBanner, IMessage>
{
	private String banner;
	private int alpha;

	public MessageToggleBanner()
	{
	}

	public MessageToggleBanner(String id, int a)
	{
		banner = id;
		alpha = a;
	}

	@Override
	public void toBytes(ByteBuf data)
	{
		ByteBufUtils.writeUTF8String(data, banner);
		data.writeByte(alpha);
	}

	@Override
	public void fromBytes(ByteBuf data)
	{
		banner = ByteBufUtils.readUTF8String(data);
		alpha = data.readUnsignedByte();
	}

	@Override
	public IMessage onMessage(MessageToggleBanner message, MessageContext ctx)
	{
		onMessage(message.banner, message.alpha);
		return null;
	}

	private void onMessage(String b, int a)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			Banner banner = FTBBannersClientEventHandler.BANNERS.get(b);

			if (banner != null)
			{
				banner.alpha = a;
			}
		});
	}
}