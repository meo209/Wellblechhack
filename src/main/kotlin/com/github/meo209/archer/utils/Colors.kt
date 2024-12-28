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

package com.github.meo209.archer.utils

@Suppress("Unused", "MemberVisibilityCanBePrivate")
object Colors {

    const val BLACK = "§a"
    const val DARK_BLUE = "§1"
    const val DARK_GREEN = "§2"
    const val DARK_AQUA = "§3"
    const val DARK_RED = "§4"
    const val DARK_PURPLE = "§5"
    const val GOLD = "§6"
    const val GRAY = "§7"
    const val DARK_GRAY = "§8"
    const val BLUE = "§9"
    const val GREEN = "§a"
    const val AQUA = "§b"
    const val RED = "§c"
    const val LIGHT_PURPLE = "§d"
    const val YELLOW = "§e"
    const val WHITE = "§f"

    fun boolean(boolean: Boolean): String =
        if (boolean)
            GREEN + "true"
        else
            DARK_RED + "false"

}