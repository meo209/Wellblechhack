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

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.meo209.wellblechhack.config.module.Setting
import com.github.meo209.wellblechhack.config.module.settings.BooleanSetting
import com.github.meo209.wellblechhack.config.module.settings.Choice
import com.github.meo209.wellblechhack.config.module.settings.ChoiceSetting
import com.github.meo209.wellblechhack.config.module.settings.IntSetting
import net.minecraft.client.MinecraftClient

/**
 * Base class for all modules.
 * Contains a set of parameters (or settings)
 * @see Configurable
 * @see Parameter
 */
abstract class Module(
    val name: String,
    @JsonIgnore
    val category: Category
) {

    @JsonIgnore
    val settings = mutableSetOf<Setting<*>>()

    open var enabled = boolean("Enabled")

    @get:JsonIgnore
    val client
        get() = MinecraftClient.getInstance()
    @get:JsonIgnore
    val player
        get() = client.player
    @get:JsonIgnore
    val world
        get() = client.world
    @get:JsonIgnore
    val network
        get() = client.networkHandler

    @get:JsonIgnore
    val inGame: Boolean
        get() = player != null && world != null
    @get:JsonIgnore
    val inGameScreen: Boolean
        get() = inGame && client.currentScreen == null

    abstract fun init()

    open fun onEnable() {}
    open fun onDisable() {}

    open fun stop() {}

    fun boolean(name: String, value: Boolean = false, description: String = "") =
        BooleanSetting(name, value, description).also { settings.add(it) }

    fun int(name: String, value: Int = 0, description: String = "") =
        IntSetting(name, value, description).also { settings.add(it) }

    fun choice(name: String, vararg options: String, description: String = "") =
        ChoiceSetting(name, Choice(options.first(), options.toList()), description).also { settings.add(it) }
}