package com.faux.customentitydata.api.playersaves;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.nio.file.Path;

/**
 * An event listener that is notified when player data is read from the disk. This listener will be notified after the
 * vanilla data has been loaded.
 */
@FunctionalInterface
public interface IPlayerLoadListener {

    Event<IPlayerLoadListener> EVENT = EventFactory.createArrayBacked(IPlayerLoadListener.class, callbacks -> (player, saveDir) -> {

        for (IPlayerLoadListener callback : callbacks) {

            callback.loadPlayerData(player, saveDir);
        }
    });

    /**
     * Called when a player's data is loaded directly from the disk.
     *
     * @param player  The player that is being loaded.
     * @param saveDir The save folder.
     */
    void loadPlayerData(Player player, Path saveDir);
}
