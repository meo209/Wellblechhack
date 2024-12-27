package com.github.meo209.archer.features.module.impl

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.events.S2CPacketEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.utils.InventoryUtils
import com.github.meo209.archer.utils.PlayerInventorySlots
import com.github.meo209.keventbus.EventBus
import net.minecraft.entity.EntityStatuses
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket

class ModuleAutoTotem : Module("AutoTotem", Category.Combat) {

    var keybind = Keybind()
    var simulate: Boolean = true

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind.key })

        EventBus.global().function<S2CPacketEvent>(::onEvent) { enabled && inGame }
    }

    private fun onEvent(event: S2CPacketEvent) {
        if (event.packet !is EntityStatusS2CPacket) return

        if (event.packet.status != EntityStatuses.USE_TOTEM_OF_UNDYING) return

        InventoryUtils.moveFirstMatchingItem({ stack ->
            stack.item == Items.TOTEM_OF_UNDYING
        }, PlayerInventorySlots.OFFHAND, simulate)
    }

}