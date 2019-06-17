package com.feed_the_beast.ftbbanners;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Loader;

/**
 * @author LatvianModder
 */
public class BannerBlockEntity extends TileEntity
{
	public ResourceLocation image = new ResourceLocation("textures/gui/presets/isles.png");
	public float width = 1F;
	public float height = 1F;
	public float rotation = 0F;
	public int alpha = 255;
	public float wind = 0.1F;
	public boolean glow = false;
	public String gameStage = "";
	public boolean culling = false;
	public double offsetX = 0D;
	public double offsetY = 0D;
	public double offsetZ = 0D;
	public boolean hover = false;

	public NBTTagCompound write(NBTTagCompound nbt)
	{
		nbt.setString("image", image.toString());
		nbt.setFloat("width", width);
		nbt.setFloat("height", height);
		nbt.setFloat("rotation", rotation);
		nbt.setInteger("alpha", alpha);
		nbt.setFloat("wind", wind);
		nbt.setBoolean("glow", glow);
		nbt.setString("game_stage", gameStage);
		nbt.setBoolean("culling", culling);
		nbt.setDouble("offset_x", offsetX);
		nbt.setDouble("offset_y", offsetY);
		nbt.setDouble("offset_z", offsetZ);
		nbt.setBoolean("hover", hover);
		return nbt;
	}

	public void read(NBTTagCompound nbt)
	{
		image = new ResourceLocation(nbt.getString("image"));
		width = nbt.getFloat("width");
		height = nbt.getFloat("height");
		rotation = nbt.getFloat("rotation");
		alpha = nbt.getInteger("alpha");
		wind = nbt.getFloat("wind");
		glow = nbt.getBoolean("glow");
		gameStage = nbt.getString("game_stage");
		culling = nbt.getBoolean("culling");
		offsetX = nbt.getDouble("offset_x");
		offsetY = nbt.getDouble("offset_y");
		offsetZ = nbt.getDouble("offset_z");
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

	public int getAlpha(EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode)
		{
			return alpha;
		}
		else if (!gameStage.isEmpty() && Loader.isModLoaded("gamestages"))
		{
			if (!hasGameStage(player))
			{
				return 0;
			}
		}

		return alpha;
	}

	private boolean hasGameStage(EntityPlayer player)
	{
		return GameStageHelper.hasStage(player, gameStage);
	}
}