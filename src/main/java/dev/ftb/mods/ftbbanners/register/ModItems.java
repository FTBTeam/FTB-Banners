package dev.ftb.mods.ftbbanners.register;

import dev.ftb.mods.ftbbanners.FTBBanners;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FTBBanners.MOD_ID);

    public static final RegistryObject<Item> BANNER_ITEM_BLOCK_ITEM
            = ITEMS.register("banner_item_block", () -> new BlockItem(ModBlocks.BANNER_ITEM_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BANNER_TEXT_BLOCK_ITEM
            = ITEMS.register("banner_text_block", () -> new BlockItem(ModBlocks.BANNER_TEXT_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BANNER_IMAGE_BLOCK_ITEM
            = ITEMS.register("banner_image_block", () -> new BlockItem(ModBlocks.BANNER_IMAGE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
