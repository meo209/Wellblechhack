package com.github.meo209.archer.mixin;

import com.github.meo209.archer.Archer;
import com.github.meo209.archer.events.ClientShutdownEvent;
import com.github.meo209.keventbus.EventBus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(RunArgs args, CallbackInfo ci) {
        Archer.INSTANCE.init();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void stop(CallbackInfo ci) {
        EventBus.Companion.global().post(new ClientShutdownEvent());
    }

}
