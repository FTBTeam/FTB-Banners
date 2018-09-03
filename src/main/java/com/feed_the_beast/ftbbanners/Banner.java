package com.feed_the_beast.ftbbanners;

import com.feed_the_beast.ftbbanners.net.FTBBannersNetHandler;
import com.feed_the_beast.ftbbanners.net.MessageSyncOneBanner;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author LatvianModder
 */
public final class Banner
{
	public final String id;
	public int dimension = 0;
	public double x = 0, y = 0, z = 0;
	public ResourceLocation image = new ResourceLocation("textures/gui/presets/isles.png");
	public float width = 1F;
	public float height = 1F;
	public float rotation = 0F;
	public int alpha = 255;
	public float wind = 0.1F;
	public boolean shouldSave = false;

	public Banner(String _id)
	{
		id = _id;
	}

	public void write(NBTTagCompound nbt)
	{
		nbt.setInteger("Dimension", dimension);
		nbt.setDouble("X", x);
		nbt.setDouble("Y", y);
		nbt.setDouble("Z", z);
		nbt.setString("Image", image.toString());
		nbt.setFloat("Width", width);
		nbt.setFloat("Height", height);
		nbt.setFloat("Rotation", rotation);
		nbt.setInteger("Alpha", alpha);
		nbt.setFloat("Wind", wind);
	}

	public void read(NBTTagCompound nbt)
	{
		dimension = nbt.getInteger("Dimension");
		x = nbt.getDouble("X");
		y = nbt.getDouble("Y");
		z = nbt.getDouble("Z");
		image = new ResourceLocation(nbt.getString("Image"));
		width = nbt.getFloat("Width");
		height = nbt.getFloat("Height");
		rotation = nbt.getFloat("Rotation");
		alpha = nbt.getInteger("Alpha");
		wind = nbt.getFloat("Wind");
	}

	public void write(ByteBuf data)
	{
		data.writeInt(dimension);
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		ByteBufUtils.writeUTF8String(data, image.toString());
		data.writeFloat(width);
		data.writeFloat(height);
		data.writeFloat(rotation);
		data.writeByte(alpha);
		data.writeFloat(wind);
	}

	public void read(ByteBuf data)
	{
		dimension = data.readInt();
		x = data.readDouble();
		y = data.readDouble();
		z = data.readDouble();
		image = new ResourceLocation(ByteBufUtils.readUTF8String(data));
		width = data.readFloat();
		height = data.readFloat();
		rotation = data.readFloat();
		alpha = data.readUnsignedByte();
		wind = data.readFloat();
	}

	public String toString()
	{
		return id;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public boolean equals(Object o)
	{
		return o == this || o != null && id.equals(o.toString());
	}

	public void save(File folder)
	{
		File file = new File(folder, id + ".nbt");

		if (height == 0)
		{
			file.delete();
			FTBBannersNetHandler.NET.sendToAll(new MessageSyncOneBanner(this));
			return;
		}

		if (!file.exists() && !file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}

		NBTTagCompound nbt = new NBTTagCompound();
		write(nbt);

		ThreadedFileIOBase.getThreadedIOInstance().queueIO(() -> {
			try (FileOutputStream out = new FileOutputStream(file))
			{
				CompressedStreamTools.writeCompressed(nbt, out);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			return false;
		});
	}

	public void saveAndSend()
	{
		shouldSave = true;
		FTBBannersNetHandler.NET.sendToAll(new MessageSyncOneBanner(this));
	}
}