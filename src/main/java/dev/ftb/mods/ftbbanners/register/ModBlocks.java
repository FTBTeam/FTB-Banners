package dev.ftb.mods.ftbbanners.register;

import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.image.ImageBannerBlock;
import dev.ftb.mods.ftbbanners.banners.item.ItemBannerBlock;
import dev.ftb.mods.ftbbanners.banners.text.TextBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FTBBanners.MOD_ID);

    public static final RegistryObject<Block> BANNER_ITEM_BLOCK
            = BLOCKS.register("banner_item_block", ItemBannerBlock::new);
    public static final RegistryObject<Block> BANNER_TEXT_BLOCK
            = BLOCKS.register("banner_text_block", TextBannerBlock::new);
    public static final RegistryObject<Block> BANNER_IMAGE_BLOCK
            = BLOCKS.register("banner_image_block", ImageBannerBlock::new);
}
