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

package com.github.meo209.archer.features.module.config

import com.google.gson.*
import java.lang.reflect.Type

class ConfigurableSerializer: JsonSerializer<Configurable<*>>, JsonDeserializer<Configurable<*>> {
    
    private val pkg = "com.github.meo209.archer.features.module.config.types."
    private val suffix = Configurable::class.java.simpleName
    
    override fun serialize(configurable: Configurable<*>, type: Type, context: JsonSerializationContext): JsonElement {
        val element = Gson().toJsonTree(configurable)
        element.asJsonObject.addProperty("@type", configurable.javaClass.simpleName.removeSuffix(suffix))
        return element
    }

    override fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Configurable<*> {
        val jsonObject = element.asJsonObject
        val typeName = jsonObject["@type"].asString
        
        val clazz = Class.forName("${pkg}${typeName}${suffix}")
        return Gson().fromJson(element, clazz) as Configurable<*>
    }

}