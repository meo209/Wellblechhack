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

package com.github.meo209.wellblechhack.features.module.serialization

import com.github.meo209.wellblechhack.features.module.Configurable
import com.github.meo209.wellblechhack.features.module.ModuleManager
import com.github.meo209.wellblechhack.features.module.config.parameter.Parameter
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConfigurableDeserializer : JsonDeserializer<Configurable> {

    override fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Configurable {
        val jsonObject = element.asJsonObject
        val name = jsonObject["name"].asString

        val configurable = ModuleManager.modules.find { it.name == name }
            ?: throw JsonParseException("Module with name $name not found. Maybe try updating or deleting modules.json")

        // Pass the parent Configurable object as part of the context
        val parameters = context.deserialize<HashSet<Parameter<*>>>(
            jsonObject["parameters"],
            object : TypeToken<HashSet<Parameter<*>>>() {}.type,
        )

        configurable.parameters.forEach { param ->
            parameters.first { it.name == param.name && it.type == param.type }
                .syncFrom(param)
        }

        return configurable
    }
}