package com.faux.customentitydata.mixin;

import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(PlayerDataStorage.class)
public interface AccessorPlayerDataStorage {

    @Accessor("playerDir")
    File faux$getSaveDir();
}
