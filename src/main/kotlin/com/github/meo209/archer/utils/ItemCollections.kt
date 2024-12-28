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

import net.minecraft.item.Items.*

object ItemCollections {

    val HELMETS = arrayOf(LEATHER_HELMET, CHAINMAIL_HELMET, GOLDEN_HELMET, IRON_HELMET, DIAMOND_HELMET, NETHERITE_HELMET)
    val CHESTPLATES = arrayOf(LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, GOLDEN_CHESTPLATE, IRON_CHESTPLATE, DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE)
    val LEGGINGS = arrayOf(LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, GOLDEN_LEGGINGS, IRON_LEGGINGS, DIAMOND_LEGGINGS, NETHERITE_LEGGINGS)
    val BOOTS = arrayOf(LEATHER_BOOTS, CHAINMAIL_BOOTS, GOLDEN_BOOTS, IRON_BOOTS, DIAMOND_BOOTS, NETHERITE_BOOTS)
    
    val ARMOR = arrayOf(HELMETS, CHESTPLATES, LEGGINGS, BOOTS)
}