package dev.ftb.mods.ftbbanners.integration;

import net.darkhax.gamestages.data.GameStageSaveHandler;

public class Gamestages {
    public static boolean hasGameStage(String gameStage) {
        return gameStage.startsWith("!") ?
                !GameStageSaveHandler.getClientData().hasStage(gameStage.substring(1)) :
                GameStageSaveHandler.getClientData().hasStage(gameStage);

    }
}
