package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.events.PlayerInventorySlotChangeEvent
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.Setting
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.utils.InventoryUtils
import com.github.meo209.archer.utils.PlayerInventorySlots
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient
import net.minecraft.item.Items

class AutoTotem : Module("Auto Totem", Category.PLAYER) {

    @Setting
    var keybind: Keybind = Keybind()

    override fun register() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind.key })

        EventBus.global().handler(PlayerInventorySlotChangeEvent::class, { event ->
            if (event.slot == PlayerInventorySlots.OFFHAND && event.old.item == Items.TOTEM_OF_UNDYING && MinecraftClient.getInstance().currentScreen == null) {
                InventoryUtils.moveFirstMatchingItem(
                    { it.item == Items.TOTEM_OF_UNDYING }, PlayerInventorySlots.OFFHAND
                )
            }
        }, { enabled })
    }
}