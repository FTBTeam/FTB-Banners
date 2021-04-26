package dev.ftb.mods.ftbbanners.layers;

import net.darkhax.gamestages.data.GameStageSaveHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.ModList;

public class BannerLayer {
    public boolean glow = false;
    public boolean culling = false;
    public String gameStage = "";
    public boolean visible = true;

    public void write(CompoundNBT nbt) {
        if (!this.gameStage.isEmpty() || ModList.get().isLoaded("gamestages")) {
            nbt.putString("game_stage", this.gameStage);
        }
        nbt.putBoolean("glow", this.glow);
        nbt.putBoolean("culling", this.culling);
        nbt.putString("game_stage", this.gameStage);
        nbt.putBoolean("visible", this.visible);
    }

    public void read(CompoundNBT nbt) {
        this.glow = nbt.getBoolean("glow");
        this.culling = nbt.getBoolean("culling");
        this.gameStage = nbt.getString("game_stage");
        this.visible = nbt.getBoolean("visible");
    }

    public boolean isVisible(PlayerEntity player) {
        if (player.isCreative()) {
            return true;
        } else if (!this.gameStage.isEmpty() && ModList.get().isLoaded("gamestages")) {
            return this.hasGameStage();
        }

        return this.visible;
    }

    private boolean hasGameStage() {
        if (this.gameStage.charAt(0) == '!') {
            return !GameStageSaveHandler.getClientData().hasStage(this.gameStage.substring(1));
        }

        return GameStageSaveHandler.getClientData().hasStage(this.gameStage);
    }
}
