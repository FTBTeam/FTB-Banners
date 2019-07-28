package com.feed_the_beast.ftbbanners;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

/**
 * @author LatvianModder
 */
public class BannerBlockEntity extends TileEntity
{
	public BannerLayer[] layers = {new BannerLayer()};
	public float width = 1F;
	public float height = 1F;
	public float rotationX = 0F;
	public float rotationY = 0F;
	public float rotationZ = 0F;
	public int alpha = 255;
	public float wind = 0.1F;
	public float windSpeed = 1F;
	public float offsetX = 0F;
	public float offsetY = 0F;
	public float offsetZ = 0F;
	public boolean hover = false;

	public NBTTagCompound write(NBTTagCompound nbt)
	{
		NBTTagList layerListTag = new NBTTagList();

		for (BannerLayer layer : layers)
		{
			NBTTagCompound nbt1 = new NBTTagCompound();
			nbt1.setString("image", layer.image.toString());
			nbt1.setBoolean("glow", layer.glow);
			nbt1.setBoolean("culling", layer.culling);

			if (!layer.gameStage.isEmpty() || Loader.isModLoaded("gamestages"))
			{
				nbt1.setString("game_stage", layer.gameStage);
			}

			layerListTag.appendTag(nbt1);
		}

		nbt.setTag("layers", layerListTag);
		nbt.setFloat("width", width);
		nbt.setFloat("height", height);
		nbt.setFloat("rotation_x", rotationX);
		nbt.setFloat("rotation_y", rotationY);
		nbt.setFloat("rotation_z", rotationZ);
		nbt.setInteger("alpha", alpha);
		nbt.setFloat("wind", wind);
		nbt.setFloat("wind_speed", windSpeed);
		nbt.setFloat("offset_x", offsetX);
		nbt.setFloat("offset_y", offsetY);
		nbt.setFloat("offset_z", offsetZ);
		nbt.setBoolean("hover", hover);
		return nbt;
	}

	public void read(NBTTagCompound nbt)
	{
		ArrayList<BannerLayer> layerList = new ArrayList<>();
		NBTTagList layerListTag = nbt.getTagList("layers", Constants.NBT.TAG_COMPOUND);

		if (layerListTag.isEmpty())
		{
			String img = nbt.getString("image");

			if (!img.isEmpty())
			{
				BannerLayer layer = new BannerLayer();
				layer.image = new ResourceLocation(img);
				layer.glow = nbt.getBoolean("glow");
				layer.culling = nbt.getBoolean("culling");
				layer.gameStage = nbt.getString("game_stage");
				layerList.add(layer);
			}
		}
		else
		{
			for (int i = 0; i < layerListTag.tagCount(); i++)
			{
				NBTTagCompound nbt1 = layerListTag.getCompoundTagAt(i);
				BannerLayer layer = new BannerLayer();
				layer.image = new ResourceLocation(nbt1.getString("image"));
				layer.glow = nbt1.getBoolean("glow");
				layer.culling = nbt1.getBoolean("culling");
				layer.gameStage = nbt1.getString("game_stage");
				layerList.add(layer);
			}
		}

		layers = layerList.toArray(new BannerLayer[0]);
		width = nbt.getFloat("width");
		height = nbt.getFloat("height");

		rotationX = nbt.getFloat("rotation_x");
		rotationY = nbt.getFloat("rotation_y");
		
		if(rotationY == 0F)
		{
			rotationY = nbt.getFloat("rotation");
		}
		
		rotationZ = nbt.getFloat("rotation_z");
		
		alpha = nbt.getInteger("alpha");
		wind = nbt.getFloat("wind");

		if (nbt.hasKey("wind_speed"))
		{
			windSpeed = nbt.getFloat("wind_speed");
		}

		offsetX = nbt.getFloat("offset_x");
		offsetY = nbt.getFloat("offset_y");
		offsetZ = nbt.getFloat("offset_z");
		hover = nbt.getBoolean("hover");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		write(nbt);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		read(nbt);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound nbt)
	{
		readFromNBT(nbt);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(pos, 0, write(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		read(pkt.getNbtCompound());
	}

	@Override
	public double getMaxRenderDistanceSquared()
	{
		return 128D * 128D;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
}