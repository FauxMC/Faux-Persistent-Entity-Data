package com.faux.persistententitydata.api;

import net.minecraft.nbt.CompoundTag;

/**
 * Custom data is data that is held for the holder's life, once the holder is killed, it is replaced.
 */
public interface ICustomDataHolder {

    CompoundTag fepd$getCustomData();

    void fepd$setLifetimeData(CompoundTag tag);

}
