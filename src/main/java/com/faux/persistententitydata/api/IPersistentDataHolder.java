package com.faux.persistententitydata.api;

import net.minecraft.nbt.CompoundTag;

/**
 * Persistent data is data that is stored for a specific holder, regardless of if they have been replaced or not.
 */
public interface IPersistentDataHolder {

    CompoundTag fepd$getPersistentData();

    void fepd$setPersistentData(CompoundTag tag);

}
