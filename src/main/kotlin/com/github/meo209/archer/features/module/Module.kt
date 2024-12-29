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
import com.github.meo209.archer.features.module.config.parameter.Parameter
import com.github.meo209.archer.features.module.config.parameter.ParameterType
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

/**
 * Base class for all modules.
 * Contains a set of parameters (or settings)
 * @see Configurable
 * @see Parameter
 */
abstract class Module(name: String, val category: Category) : Configurable(name) {

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
        ModuleSerialization.serialize(this)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Parameter<*>> get(name: String): T? =
        parameters.firstOrNull { it.name == name } as T?

    private fun <T> parameter(name: String, default: T, parameterType: ParameterType) =
        Parameter<T>(name, default, parameterType).also { parameters.add(it) }
    
    fun boolean(name: String, default: Boolean = false) = parameter(name, default, ParameterType.BOOLEAN)
    
    fun string(name: String, default: String = "") = parameter(name, default, ParameterType.STRING)
    
    fun keybinding(name: String, default: Int = -1) = parameter(name, default, ParameterType.KEYBINDING)
    
    fun int(name: String, default: Int = 0) = parameter(name, default, ParameterType.INT)
    
    fun double(name: String, default: Double = 0.0) = parameter(name, default, ParameterType.DOUBLE)
    
    fun float(name: String, default: Float = 0f) = parameter(name, default, ParameterType.FLOAT)
}