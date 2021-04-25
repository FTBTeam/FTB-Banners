package dev.ftb.mods.ftbbanners;

import net.darkhax.gamestages.data.GameStageSaveHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

/**
 * @author LatvianModder
 */
public class BannerLayer {
    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/presets/isles.png");

    public ResourceLocation image = DEFAULT_IMAGE;
    public boolean glow = false;
    public boolean culling = false;
    public String gameStage = "";

    public boolean isVisible(PlayerEntity player) {
        if (player.isCreative()) {
            return true;
        } else if (!this.gameStage.isEmpty() && ModList.get().isLoaded("gamestages")) {
            return this.hasGameStage();
        }

        return true;
    }

    private boolean hasGameStage() {
        if (this.gameStage.charAt(0) == '!') {
            return !GameStageSaveHandler.getClientData().hasStage(this.gameStage.substring(1));
        }

        return GameStageSaveHandler.getClientData().hasStage(this.gameStage);
    }
}
