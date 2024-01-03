package com.faux.customentitydata.api.playersaves;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.nio.file.Path;

/**
 * An event listener that is notified when the player data is saved to disk. The listener will be notified after the
 * vanilla save data has been written.
 */
@FunctionalInterface
public interface IPlayerSaveListener {

    Event<IPlayerSaveListener> EVENT = EventFactory.createArrayBacked(IPlayerSaveListener.class, callbacks -> (player, saveDir) -> {

        for (IPlayerSaveListener callback : callbacks) {

            callback.savePlayerData(player, saveDir);
        }
    });

    /**
     * Called when a player's data is saved directly to the disk.
     *
     * @param player  The player that is being saved.
     * @param saveDir The save folder.
     */
    void savePlayerData(Player player, Path saveDir);
}