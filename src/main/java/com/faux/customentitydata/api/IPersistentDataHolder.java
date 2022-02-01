package com.faux.customentitydata.api;

import net.minecraft.nbt.CompoundTag;

/**
 * Persistent data is data that is stored while the entity is alive and is copied to a new entity when the entity is cloned.
 * Persistent data is saved to disk and is usually stored inside the custom data from {@link ICustomDataHolder}.
 */
public interface IPersistentDataHolder {

    CompoundTag faux$getPersistentData();

    void faux$setPersistentData(CompoundTag tag);

}
