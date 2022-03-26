package com.faux.customentitydata.api.playersaves;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * This class provides a custom player data solution that stores player data in a separate auxiliary file in the player
 * directory. While this is not the definitive way to store custom player data it is often the cleanest approach.
 * <p>
 * Some benefits of this approach include:
 * <ul>
 *     <li>No risk of corrupting vanilla player data.</li>
 *     <li>Custom data survives player respawn.</li>
 *     <li>Custom data survives mod being uninstalled temporarily.</li>
 * </ul>
 * <p>
 * This implementation will invoke {@link #savePlayer(Player)} when the vanilla player data is written to disk. You can
 * use this method to pull data from your own in-memory sources and store it in the resulting NBT tag. Once this tag is
 * generated it will be saved to the disk as a compressed NBT file. A backup save file will also be generated and
 * maintained to help players rollback their data in the event of corruption or other adverse circumstances. Loading
 * your custom data is done in {@link #loadPlayer(Player, CompoundTag)} which is invoked after vanilla has loaded their
 * player data. You can use this method to restore your in-memory player data.
 * <p>
 * Files will be saved within subdirectories of the vanilla player folder. These subdirectories are based on the
 * {@link #handlerId} provided when constructing your implementation.
 */
public abstract class CustomPlayerSave implements IPlayerSaveListener, IPlayerLoadListener {

    /**
     * An identifier used when reading and writing custom player data. The ID is incorporated into the save filepath to
     * prevent reduce the likelihood of conflicting entries.
     */
    private final ResourceLocation handlerId;

    /**
     * A logger instance used to track warnings and debug information about the save data.
     */
    private final Logger log;

    public CustomPlayerSave(ResourceLocation handlerId) {

        this.handlerId = handlerId;
        this.log = LoggerFactory.getLogger(handlerId.toString());

        IPlayerLoadListener.EVENT.register(this);
        IPlayerSaveListener.EVENT.register(this);
    }

    /**
     * Saves custom data for a player as an NBT. This is invoked after the vanilla player data has been saved.
     *
     * @param player The player being saved.
     * @return The data to save for the player.
     */
    public abstract CompoundTag savePlayer(Player player);

    /**
     * Loads custom data for a player from NBT. This is invoked after the vanilla player data has been loaded.
     *
     * @param player   The player being loaded.
     * @param saveData The data for the player. If the player has no existing data an empty tag will be provided.
     */
    public abstract void loadPlayer(Player player, CompoundTag saveData);

    @Override
    public void loadPlayerData(Player player, File saveDir) {

        final long startTime = System.nanoTime();
        final File customSaveDir = getCustomSaveDir(saveDir);
        CompoundTag data = new CompoundTag();

        // Attempt to read the save file as NBT
        try {

            final File targetSave = new File(customSaveDir, player.getStringUUID() + ".dat");

            if (targetSave.exists() && targetSave.isFile()) {

                data = NbtIo.readCompressed(targetSave);
            }
        }

        catch (IOException e) {

            this.log.error("Failed to read custom data file for player {} ({}).", player.getName().getString(), player.getStringUUID(), e);
        }

        // Attempt to deserialize the NBT data
        try {

            this.loadPlayer(player, data);
            final long endTime = System.nanoTime();
            this.log.debug("Loaded data for {}. Took {}ns.", player.getName().getString(), endTime - startTime);
        }

        catch (Exception e) {

            this.log.error("Failed to read custom data for player {} ({}).", player.getName().getString(), player.getStringUUID(), e);
        }
    }

    @Override
    public void savePlayerData(Player player, File saveDir) {

        try {

            final long startTime = System.nanoTime();
            final File customSaveDir = getCustomSaveDir(saveDir);

            // Write save data to a temporary location.
            final File tempSave = File.createTempFile(player.getStringUUID() + "-", ".dat", customSaveDir);
            NbtIo.writeCompressed(this.savePlayer(player), tempSave);

            // Backup existing save and overwrite with new data.
            final File targetSave = new File(customSaveDir, player.getStringUUID() + ".dat");
            final File backupSave = new File(customSaveDir, player.getStringUUID() + ".dat_old");
            Util.safeReplaceFile(targetSave, tempSave, backupSave);
            final long endTime = System.nanoTime();

            this.log.debug("Saved data for {}. Took {}ns.", player.getName().getString(), endTime - startTime);
        }

        catch (IOException e) {

            this.log.error("Failed to write custom data for player {} ({}).", player.getName().getString(), player.getStringUUID(), e);
        }
    }

    /**
     * Generates a subdirectory for custom player files using {@link this#handlerId}. The directory will take on the
     * structure of /{namespace}/{path}/. If the generated directory does not exist it will try to create it.
     *
     * @param saveDir The root save directory. This is intended to be the player save directory.
     * @return The subdirectory for custom player files.
     */
    private File getCustomSaveDir(File saveDir) {

        final File customSaveDir = new File(new File(saveDir, handlerId.getNamespace()), handlerId.getPath());

        if (!customSaveDir.exists()) {

            if (!customSaveDir.mkdirs()) {

                this.log.error("Failed to create custom save directory {}.", customSaveDir.getPath());
            }
        }

        return customSaveDir;
    }
}