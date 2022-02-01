package com.faux.customentitydata.api;

import net.minecraft.nbt.CompoundTag;

/**
 * Custom data is data that is stored while the entity is alive.
 * Custom data is saved to disk, but is lost if the holder is "cloned" (like player respawning)
 */
public interface ICustomDataHolder {

    CompoundTag faux$getCustomData();

    void faux$setCustomData(CompoundTag tag);

}
