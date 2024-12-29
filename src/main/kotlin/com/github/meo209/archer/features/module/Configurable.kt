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

import com.github.meo209.archer.FileHandler
import com.github.meo209.archer.features.module.config.parameter.Parameter
import com.github.meo209.archer.features.module.config.parameter.ParameterType
import com.github.meo209.archer.features.module.config.types.Choice
import com.github.meo209.archer.features.module.config.types.NamedChoice
import java.io.File

/**
 * Base class that can be configured.
 * Has a parent which can also be null (in the case of modules)
 */
abstract class Configurable(val name: String, open val parent: Configurable? = null) {
    
    // Ignore the configFile from being serialized
    // Everything else is fine
    @Transient
    val configFile: File = File(FileHandler.MODULE_DIRECTORY, "${name}.bin")
    val parameters = mutableSetOf<Parameter<*>>()


    @Suppress("UNCHECKED_CAST")
    operator fun <T : Parameter<*>> get(name: String): T? =
        parameters.firstOrNull { it.name == name } as T?

    private fun <T> parameter(name: String, default: T, parameterType: ParameterType) =
        Parameter(name, default, parameterType).also { parameters.add(it) }

    fun boolean(name: String, default: Boolean = false) = parameter(name, default, ParameterType.BOOLEAN)

    fun string(name: String, default: String = "") = parameter(name, default, ParameterType.STRING)
    
    fun keybinding(name: String, default: Int = -1) = parameter(name, default, ParameterType.KEYBINDING)

    fun int(name: String, default: Int = 0) = parameter(name, default, ParameterType.INT)

    fun double(name: String, default: Double = 0.0) = parameter(name, default, ParameterType.DOUBLE)

    fun float(name: String, default: Float = 0f) = parameter(name, default, ParameterType.FLOAT)
    
    fun choice(name: String, vararg options: NamedChoice) = parameter(name, Choice(options.toList()), ParameterType.CHOICE)
}