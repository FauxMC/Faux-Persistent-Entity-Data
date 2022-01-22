package com.faux.persistententitydata.mixin;

import com.faux.persistententitydata.api.ICustomDataHolder;
import com.faux.persistententitydata.api.PersistentEntityDataConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity implements ICustomDataHolder {

    @Unique
    public CompoundTag faux$persistentData;

    @Override
    public CompoundTag faux$getCustomData() {
        if (faux$persistentData == null)
            faux$persistentData = new CompoundTag();

        return faux$persistentData;
    }

    @Override
    public void faux$setCustomData(CompoundTag tag) {
        faux$persistentData = tag;
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.BEFORE))
    public void saveWithoutId(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir) {
        compoundTag.put(PersistentEntityDataConstants.CUSTOM_NBT_KEY, faux$getCustomData());
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.BEFORE))
    public void load(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains(PersistentEntityDataConstants.CUSTOM_NBT_KEY, Tag.TAG_COMPOUND)) {
            faux$setCustomData(compoundTag.getCompound(PersistentEntityDataConstants.CUSTOM_NBT_KEY));
        }
    }

}
