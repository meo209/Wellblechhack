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

package com.github.meo209.wellblechhack.features.module

import com.github.meo209.wellblechhack.events.ModuleDisableEvent
import com.github.meo209.wellblechhack.events.ModuleEnableEvent
import com.github.meo209.wellblechhack.features.module.config.parameter.Parameter
import com.github.meo209.wellblechhack.features.module.serialization.Exclude
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

/**
 * Base class for all modules.
 * Contains a set of parameters (or settings)
 * @see Configurable
 * @see Parameter
 */
abstract class Module(
    name: String,
    val category: Category
) : Configurable(name) {

    @Exclude
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

    open fun stop() {}
}