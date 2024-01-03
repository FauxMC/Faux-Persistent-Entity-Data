package com.faux.customentitydata.mixin;

import com.faux.customentitydata.api.playersaves.IPlayerSaveListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(PlayerDataStorage.class)
public class MixinPlayerDataStorage {

    @Final
    @Shadow
    private File playerDir;

    @Inject(method = "save(Lnet/minecraft/world/entity/player/Player;)V", at = @At("RETURN"))
    private void onPlayerSave(Player player, CallbackInfo info) {

        IPlayerSaveListener.EVENT.invoker().savePlayerData(player, this.playerDir.toPath());
    }
}