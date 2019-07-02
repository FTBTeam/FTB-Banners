package com.feed_the_beast.ftbbanners;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

/**
 * @author LatvianModder
 */
public class BannerLayer
{
	public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/presets/isles.png");

	public ResourceLocation image = DEFAULT_IMAGE;
	public boolean glow = false;
	public boolean culling = false;
	public String gameStage = "";

	public boolean isVisible(EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode)
		{
			return true;
		}
		else if (!gameStage.isEmpty() && Loader.isModLoaded("gamestages"))
		{
			return hasGameStage(player);
		}

		return true;
	}

	private boolean hasGameStage(EntityPlayer player)
	{
		if (gameStage.charAt(0) == '!')
		{
			return !GameStageHelper.hasStage(player, gameStage.substring(1));
		}

		return GameStageHelper.hasStage(player, gameStage);
	}
}