package dev.ftb.mods.ftbbanners;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static final RegistryObject<Block> BANNER_BLOCK = BLOCKS.register("banner_block", BannerBlock::new);
    public static final RegistryObject<Item> BANNER_BLOCK_ITEM = ITEMS.register("banner_block", () -> new BlockItem(BANNER_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<TileEntityType<BannerBlockEntity>> BANNER_TILE = TILES.register("banner_tile", () -> TileEntityType.Builder.of(
        BannerBlockEntity::new, BANNER_BLOCK.get()
    ).build(null));

    public FTBBanners() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(BANNER_TILE.get(), BannerBlockRenderer::new);
    }
}
