package com.github.meo209.archer.events

import com.github.meo209.keventbus.Event
import net.minecraft.network.packet.Packet

class C2SPacketEvent(val packet: Packet<*>): Event
class S2CPacketEvent(val packet: Packet<*>): Event