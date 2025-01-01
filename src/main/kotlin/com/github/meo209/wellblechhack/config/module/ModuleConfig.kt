/*
 * Wellblechhack
 * Copyright (C) 2025 meo209
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

package com.github.meo209.wellblechhack.config.module

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.meo209.wellblechhack.FileHandler
import com.github.meo209.wellblechhack.config.Config
import com.github.meo209.wellblechhack.features.module.Module
import java.io.File

class ModuleConfig(val module: Module) : Config {

    override val file: File
        get() = File(FileHandler.MODULE_DIRECTORY, "${module.name}.xml")

    // Using XML since it supports better type-safety
    private val mapper = XmlMapper()
        .setDefaultMergeable(true)
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .registerKotlinModule()

    override fun load() {
        if (!file.exists() || file.readText().isEmpty()) return

        mapper.readerForUpdating(module)
            .readValue(file.readText(), module::class.java)
    }

    override fun save() {
        if (!file.exists())
            file.createNewFile()

        file.writeText(mapper.writeValueAsString(module))
    }


}