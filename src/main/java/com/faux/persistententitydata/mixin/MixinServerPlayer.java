package com.faux.persistententitydata.mixin;

import com.faux.persistententitydata.api.ICustomDataHolder;
import com.faux.persistententitydata.api.IPersistentDataHolder;
import com.faux.persistententitydata.api.PersistentEntityDataConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IPersistentDataHolder {

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    public void restoreFrom(ServerPlayer other, boolean keepEverything, CallbackInfo ci) {

        IPersistentDataHolder oldHolder = (IPersistentDataHolder) other;
        this.fepd$setPersistentData(oldHolder.fepd$getPersistentData());
    }

    @Override
    public CompoundTag fepd$getPersistentData() {
        CompoundTag lifetimeData = ((ICustomDataHolder) this).fepd$getCustomData();
        if (!lifetimeData.contains(PersistentEntityDataConstants.PERSISTENT_NBT_KEY)) {
            CompoundTag tag = new CompoundTag();
            lifetimeData.put(PersistentEntityDataConstants.PERSISTENT_NBT_KEY, tag);
            return tag;
        }
        return lifetimeData.getCompound(PersistentEntityDataConstants.PERSISTENT_NBT_KEY);
    }

    @Override
    public void fepd$setPersistentData(CompoundTag tag) {
        ((ICustomDataHolder) this).fepd$getCustomData().put(PersistentEntityDataConstants.PERSISTENT_NBT_KEY, tag);
    }
}
