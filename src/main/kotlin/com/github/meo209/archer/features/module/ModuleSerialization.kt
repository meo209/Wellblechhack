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

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.ByteBufferInput
import com.esotericsoftware.kryo.io.ByteBufferOutput
import com.github.meo209.archer.features.module.config.parameter.Parameter

object ModuleSerialization {

    private val kryo = Kryo()

    init {
        // unsafe but should work fine
        kryo.isRegistrationRequired = false
    }

    @Suppress("UNCHECKED_CAST")
    fun deserialize(configurable: Configurable) {
        val file = configurable.configFile
        if (!file.exists())
            file.createNewFile()

        if (file.readText().isEmpty()) return

        // Read the file content into a byte array
        val fileContent = file.readBytes()

        // Initialize ByteBufferInput with the file content
        val input = ByteBufferInput(fileContent)
        val parameters = kryo.readObject(input, HashSet::class.java) as Set<Parameter<*>>

        configurable.parameters.forEach { param ->
            val dataParam = parameters.first { it.name == param.name && it.type == param.type }

            // Sync the parameter to the deserialized parameter
            param.syncFrom(dataParam)
        }
    }

    fun serialize(configurable: Configurable) {
        val file = configurable.configFile

        // Initialize ByteBufferOutput with a rather large size
        val buffer = ByteBufferOutput(1024)
        kryo.writeObject(buffer, configurable.parameters)

        // Write the serialized data to the file
        file.writeBytes(buffer.toBytes())
    }

}