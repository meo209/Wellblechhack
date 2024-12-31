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

import com.github.meo209.wellblechhack.FileHandler
import com.github.meo209.wellblechhack.features.module.config.parameter.Parameter
import com.github.meo209.wellblechhack.features.module.serialization.ConfigurableDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object ModuleSerialization {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Configurable::class.java, ConfigurableDeserializer())
        .setPrettyPrinting()
        .create()

    fun deserialize() {
        val fileContent = FileHandler.MODULE_FILE.readText()
        if (fileContent.isEmpty()) return

        val type = object : TypeToken<ArrayList<Configurable>>() {}.type
        gson.fromJson<ArrayList<Configurable>>(fileContent, type)
    }

    fun serialize() {
        FileHandler.MODULE_FILE.writeText(gson.toJson(ModuleManager.modules))
    }

}