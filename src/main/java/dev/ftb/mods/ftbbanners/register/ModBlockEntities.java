package dev.ftb.mods.ftbbanners.register;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerEntity;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerEntity;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FTBBanners.MOD_ID);

    public static final RegistryObject<BlockEntityType<ItemBannerEntity>> BANNER_ITEM_TILE = TILES.register("banner_item_tile",
            () -> BlockEntityType.Builder.of(ItemBannerEntity::new, ModBlocks.BANNER_ITEM_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<TextBannerEntity>> BANNER_TEXT_TILE = TILES.register("banner_text_tile",
            () -> BlockEntityType.Builder.of(TextBannerEntity::new, ModBlocks.BANNER_TEXT_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ImageBannerEntity>> BANNER_IMAGE_TILE = TILES.register("banner_image_tile",
            () -> BlockEntityType.Builder.of(ImageBannerEntity::new, ModBlocks.BANNER_IMAGE_BLOCK.get()).build(null));
}
