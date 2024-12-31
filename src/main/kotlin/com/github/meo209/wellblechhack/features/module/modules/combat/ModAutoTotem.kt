/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.meo209.wellblechhack.features.module.modules.combat

import com.github.meo209.wellblechhack.events.KeyPressEvent
import com.github.meo209.wellblechhack.events.S2CPacketEvent
import com.github.meo209.wellblechhack.features.module.Category
import com.github.meo209.wellblechhack.features.module.Module
import com.github.meo209.wellblechhack.utils.InventoryUtils
import com.github.meo209.wellblechhack.utils.PlayerInventorySlots
import com.github.meo209.keventbus.EventBus
import net.minecraft.entity.EntityStatuses
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket

object ModAutoTotem : Module("AutoTotem", Category.Combat) {

    var keybind by keybinding("Keybind")
    
    var simulate by boolean("Simulate")
    
    var test by int("Test")

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind })

        EventBus.global().function<S2CPacketEvent>(ModAutoTotem::onEvent) { enabled && inGame }
        
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