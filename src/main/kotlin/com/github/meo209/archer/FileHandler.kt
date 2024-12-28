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

package com.github.meo209.archer

import net.fabricmc.loader.api.FabricLoader
import java.io.File

object FileHandler {

    val ROOT_DIRECTORY = File(FabricLoader.getInstance().configDir.toFile(), "archer")
    val MODULE_DIRECTORY = File(ROOT_DIRECTORY, "modules")

    init {
        if (!ROOT_DIRECTORY.exists()) ROOT_DIRECTORY.mkdir()

        if (!MODULE_DIRECTORY.exists()) MODULE_DIRECTORY.mkdir()
    }

    // Statically load the FileManager
    fun init() {}

}