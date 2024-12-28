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

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.meo209.archer.FileHandler
import java.io.File
import kotlin.reflect.full.memberProperties

object ModuleIO {

    private val objectMapper = ObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerKotlinModule()
    }

    private fun getFileFor(module: Module): File =
        File(FileHandler.MODULE_DIRECTORY, "${module.name}.json").apply {
            if (!exists()) createNewFile()
        }

    fun load(module: Module) {
        val file = getFileFor(module)
        if (file.readText().isEmpty()) return

        objectMapper.readerForUpdating(module).readValue<Module>(file.readText())
    }

    fun save(module: Module) {
        val file = getFileFor(module)

        val json = objectMapper.writeValueAsString(module)
        file.writeText(json)
    }
}