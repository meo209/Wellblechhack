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

package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.ModuleDisableEvent
import com.github.meo209.archer.events.ModuleEnableEvent
import com.github.meo209.archer.features.module.specific.Keybinding
import com.github.meo209.archer.features.module.specific.RangedValue
import com.github.meo209.archer.features.module.specific.Selection
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
abstract class Module(val name: String, val category: Category) {
    
    val properties = mutableSetOf<ModuleProperty<*>>()
    
    @get:JsonProperty
    open var enabled by boolean("Enabled", false)

    val client
        get() = MinecraftClient.getInstance()
    val player
        get() = client.player
    val world
        get() = client.world
    val network
        get() = client.networkHandler

    val inGame: Boolean
        get() = player != null && world != null
    val inGameScreen: Boolean
        get() = inGame && client.currentScreen == null

    abstract fun init()

    fun toggle() {
        enabled = !enabled
        if (enabled)
            EventBus.global().post(ModuleEnableEvent(this))
        else
            EventBus.global().post(ModuleDisableEvent(this))
    }

    open fun stop() {
        ModuleIO.save(this)
    }

    fun boolean(name: String, default: Boolean) =
        ModuleProperty<Boolean>(name, default).also { properties.add(it) }

    fun string(name: String, default: String) =
        ModuleProperty<String>(name, default).also { properties.add(it) }

    fun int(name: String, default: Int) =
        ModuleProperty<Int>(name, default).also { properties.add(it) }

    fun keybinding(name: String, default: Int = -1) =
        ModuleProperty<Keybinding>(name, Keybinding(default)).also { properties.add(it) }
    
    fun range(name: String, value: Float, range: ClosedFloatingPointRange<Float>) =
        ModuleProperty<RangedValue>(name, RangedValue(value, range)).also { properties.add(it) }
    
    fun choice(name: String, selection: String, list: List<String>) =
        ModuleProperty<RangedValue>(name, Selection(selection, list)).also { properties.add(it) }

}