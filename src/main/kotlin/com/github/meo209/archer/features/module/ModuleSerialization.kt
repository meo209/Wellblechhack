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

import com.github.meo209.archer.features.module.config.parameter.Parameter
import com.github.meo209.archer.features.module.config.serialization.ParameterSerializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object ModuleSerialization {
    
    private val gsonInstance = GsonBuilder()
        .registerTypeAdapter(Parameter::class.java, ParameterSerializer())
        .setPrettyPrinting()
        .create()
    
    fun deserialize(moduleContainer: ModuleContainer) {
        val file = moduleContainer.configFile
        if (!file.exists())
            file.createNewFile()
        
        if (file.readText().isEmpty()) return
        

        val typeToken = object : TypeToken<Set<Parameter<*>>>() {}
        
        val data = gsonInstance.fromJson(file.readText(), typeToken)
        
        moduleContainer.parameters.forEach { param ->
            val dataParam = data.first { it.name == param.name && it.type == param.type }
            
            // Sync the parameter to the deserialized parameter
            param.syncFrom(dataParam)
        }
    }

    fun serialize(moduleContainer: ModuleContainer) {
        val file = moduleContainer.configFile
        
        val data = gsonInstance.toJson(moduleContainer.parameters)
        
        file.writeText(data)
    }
    
}