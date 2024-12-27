package com.github.meo209.archer.mixin;

import com.github.meo209.archer.events.C2SPacketEvent;
import com.github.meo209.keventbus.EventBus;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
class ClientCommonNetworkHandlerMixin {
    
    @Inject(method = "sendPacket", at = @At("HEAD"))
    private void sendPacket(Packet<?> packet, CallbackInfo ci) {
        EventBus.Companion.global().post(new C2SPacketEvent(packet));
    }
    
}
