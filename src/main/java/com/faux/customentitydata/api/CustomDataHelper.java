package com.faux.customentitydata.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class CustomDataHelper {

    /**
     * Gets the custom data on the given Entity.
     *
     * @param entity The entity to get the custom data of.
     * @return The custom data stored on the given entity, or null if the entity is not a ICustomDataHolder.
     */
    public static CompoundTag getCustomData(Entity entity) {
        if (entity instanceof ICustomDataHolder holder) {
            return holder.faux$getCustomData();
        }
        return null;
    }

    /**
     * Sets the custom data on the given Entity.
     *
     * @param entity The entity to set the custom data of.
     * @param data   The custom data to set.
     */
    public static void setCustomData(Entity entity, CompoundTag data) {
        if (entity instanceof ICustomDataHolder holder) {
            holder.faux$setCustomData(data);
        }
    }

    /**
     * Gets the persistent data of the given ServerPlayer.
     *
     * @param player The player to get the persistent data of.
     * @return The persistent data stored on the given player, or null if the player is not a IPersistentDataHolder.
     */
    public static CompoundTag getPersistentData(ServerPlayer player) {
        if (player instanceof IPersistentDataHolder holder) {
            return holder.faux$getPersistentData();
        }
        return null;
    }

    /**
     * Sets the persistent data on the given ServerPlayer.
     *
     * @param player The player to set the persistent data of.
     * @param data   The custom data to set.
     */
    public static void setPersistentData(ServerPlayer player, CompoundTag data) {
        if (player instanceof IPersistentDataHolder holder) {
            holder.faux$setPersistentData(data);
        }
    }
}
