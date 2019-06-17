package com.feed_the_beast.ftbbanners;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
		modid = FTBBanners.MOD_ID,
		name = FTBBanners.MOD_NAME,
		version = FTBBanners.VERSION
)
public class FTBBanners
{
	public static final String MOD_ID = "ftbbanners";
	public static final String MOD_NAME = "FTB Banners";
	public static final String VERSION = "0.0.0.ftbbanners";

	@GameRegistry.ObjectHolder(MOD_ID + ":banner_block")
	public static Block BANNER_BLOCK;

	@GameRegistry.ObjectHolder(MOD_ID + ":banner_block")
	public static Item BANNER_BLOCK_ITEM;
}