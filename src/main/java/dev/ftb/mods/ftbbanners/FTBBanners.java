package dev.ftb.mods.ftbbanners;

import dev.ftb.mods.ftbbanners.client.ClientSetup;
import dev.ftb.mods.ftbbanners.commands.CopyRFToolsScreenCommand;
import dev.ftb.mods.ftbbanners.net.FTBBannersNet;
import dev.ftb.mods.ftbbanners.register.ModBlockEntities;
import dev.ftb.mods.ftbbanners.register.ModBlocks;
import dev.ftb.mods.ftbbanners.register.ModItems;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FTBBanners.MOD_ID)
public class FTBBanners {
	public static final String MOD_ID = "ftbbanners";

	public FTBBanners() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModBlockEntities.TILES.register(modEventBus);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::init);

		MinecraftForge.EVENT_BUS.addListener(this::commandRegister);

		FTBBannersNet.init();
	}

	public void commandRegister(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal(FTBBanners.MOD_ID).then(CopyRFToolsScreenCommand.register()));
	}
}
