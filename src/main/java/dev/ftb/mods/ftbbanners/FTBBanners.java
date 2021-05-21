package dev.ftb.mods.ftbbanners;

import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerEntity;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerRenderer;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerBlock;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerEntity;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerRenderer;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerBlock;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerRenderer;
import dev.ftb.mods.ftbbanners.commands.CopyRFToolsScreenCommand;
import net.minecraft.block.Block;
import net.minecraft.command.Commands;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(FTBBanners.MOD_ID)
public class FTBBanners {
    public static final String MOD_ID = "ftbbanners";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Block> BANNER_IMAGE_BLOCK = BLOCKS.register("banner_image_block", ImageBannerBlock::new);
    public static final RegistryObject<Block> BANNER_TEXT_BLOCK = BLOCKS.register("banner_text_block", TextBannerBlock::new);
    public static final RegistryObject<Block> BANNER_ITEM_BLOCK = BLOCKS.register("banner_item_block", ItemBannerBlock::new);

    public static final RegistryObject<Item> BANNER_IMAGE_BLOCK_ITEM = ITEMS.register("banner_image_block", () -> new BlockItem(BANNER_IMAGE_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> BANNER_TEXT_BLOCK_ITEM = ITEMS.register("banner_text_block", () -> new BlockItem(BANNER_TEXT_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> BANNER_ITEM_BLOCK_ITEM = ITEMS.register("banner_item_block", () -> new BlockItem(BANNER_ITEM_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<TileEntityType<ImageBannerEntity>> BANNER_IMAGE_TILE = TILES.register("banner_image_tile", () -> TileEntityType.Builder.of(ImageBannerEntity::new, BANNER_IMAGE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TextBannerEntity>> BANNER_TEXT_TILE = TILES.register("banner_text_tile", () -> TileEntityType.Builder.of(TextBannerEntity::new, BANNER_TEXT_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<ItemBannerEntity>> BANNER_ITEM_TILE = TILES.register("banner_item_tile", () -> TileEntityType.Builder.of(ItemBannerEntity::new, BANNER_ITEM_BLOCK.get()).build(null));

    public FTBBanners() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(BANNER_IMAGE_TILE.get(), ImageBannerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BANNER_TEXT_TILE.get(), TextBannerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BANNER_ITEM_TILE.get(), ItemBannerRenderer::new);
    }

    @SubscribeEvent
    public void commandRegister(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(FTBBanners.MOD_ID).then(CopyRFToolsScreenCommand.register()));
    }
}
