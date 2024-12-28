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
import com.github.meo209.archer.features.module.config.Configurable
import com.github.meo209.archer.features.module.config.ConfigurableSerializer
import com.github.meo209.archer.features.module.config.ExclusionStrategy
import com.github.meo209.archer.features.module.config.types.BooleanConfigurable
import com.github.meo209.archer.features.module.config.types.IntConfigurable
import com.github.meo209.archer.features.module.config.types.KeybindingConfigurable
import com.github.meo209.archer.features.module.config.types.StringConfigurable
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type


object ModuleIO {
    
    private val gson = GsonBuilder()
        .registerTypeAdapter(BooleanConfigurable::class.java, ConfigurableSerializer())
        .registerTypeAdapter(IntConfigurable::class.java, ConfigurableSerializer())
        .registerTypeAdapter(KeybindingConfigurable::class.java, ConfigurableSerializer())
        .registerTypeAdapter(StringConfigurable::class.java, ConfigurableSerializer())
        .registerTypeAdapter(Configurable::class.java, ConfigurableSerializer())
        .addSerializationExclusionStrategy(ExclusionStrategy())
        .setPrettyPrinting()
        .create()

    private fun getFileFor(module: Module): File =
        File(FileHandler.MODULE_DIRECTORY, "${module.name}.json").apply {
            if (!exists()) createNewFile()
        }

    fun load(module: Module) {
        val file = getFileFor(module)
        if (file.readText().isEmpty()) return

        val typeToken: Type = object : TypeToken<ArrayList<Configurable<*>>>() {}.type
        
        val properties = gson.fromJson<List<Configurable<*>>>(file.readText(), typeToken)
        
        println(properties.joinToString(", ") { it.name })
        
        module.properties.clear()
        module.properties.addAll(
            gson.fromJson(file.readText(), typeToken)
        )
    }

    fun save(module: Module) {
        val file = getFileFor(module)

        val json = gson.toJson(module.properties)
        file.writeText(json)
    }
}