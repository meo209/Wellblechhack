/*
 * This file is part of Archer (https://github.com/meo209/Archer)
 * Copyright (c) 2024 - meo209
 *
 * Archer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Archer program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.meo209.archer.features.module.modules.combat

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.events.S2CPacketEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.utils.InventoryUtils
import com.github.meo209.archer.utils.PlayerInventorySlots
import com.github.meo209.keventbus.EventBus
import net.minecraft.entity.EntityStatuses
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket

object ModuleAutoTotem : Module("AutoTotem", Category.Combat) {

    @get:JsonProperty
    var keybind by keybinding("Keybind")
    
    @get:JsonProperty
    var simulate by boolean("Simulate")
    
    @get:JsonProperty
    var test by int("Test")

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind })

        EventBus.global().function<S2CPacketEvent>(ModuleAutoTotem::onEvent) { enabled && inGame }
        
        println(enabled)
    }

    private fun onEvent(event: S2CPacketEvent) {
        if (event.packet !is EntityStatusS2CPacket) return

        if (event.packet.status != EntityStatuses.USE_TOTEM_OF_UNDYING) return

        InventoryUtils.moveFirstMatchingItem({ stack ->
            stack.item == Items.TOTEM_OF_UNDYING
        }, PlayerInventorySlots.OFFHAND, simulate)
    }

}