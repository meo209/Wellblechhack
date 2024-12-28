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

import java.awt.Color

/**
 * An object that contains multiple color palettes.
 */
object ColorPalette {

    object PASTEL {
        const val LIGHT_BLUE = 0xB0D0D3
        const val PUCE = 0xC08497
        const val MELON = 0xF7AF9D
        const val PEACH = 0xF7E3AF
        const val LEMON_CHIFFON = 0xF3EEC3
    }

    object PASTEL_DARK {
        const val DARK_PURPLE = 0x2A1F2D
        const val RAISIN_BLACK = 0x3B2C35
        const val FELDGRAU = 0x5B6C5D
        const val MINT = 0x59C9A5
        const val AQUAMARINE = 0x56E39F
    }

}

fun Int.color(): Color =
    Color(this)