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

import com.github.meo209.archer.events.ModuleDisableEvent
import com.github.meo209.archer.events.ModuleEnableEvent
import com.github.meo209.archer.features.module.config.Configurable
import com.github.meo209.archer.features.module.config.types.BooleanConfigurable
import com.github.meo209.archer.features.module.config.types.IntConfigurable
import com.github.meo209.archer.features.module.config.types.KeybindingConfigurable
import com.github.meo209.archer.features.module.config.types.StringConfigurable
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

abstract class Module(val name: String, val category: Category) {

    internal val properties = mutableSetOf<Configurable<*>>()

    open var enabled by boolean("Enabled")

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

    fun string(name: String) = StringConfigurable(name).also { properties += it }

    fun keybinding(name: String) = KeybindingConfigurable(name).also { properties += it }

    fun int(name: String) = IntConfigurable(name).also { properties += it }

    fun boolean(name: String) = BooleanConfigurable(name).also { properties += it }

}