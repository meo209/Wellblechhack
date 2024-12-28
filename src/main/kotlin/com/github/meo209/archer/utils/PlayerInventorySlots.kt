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

import net.minecraft.entity.player.PlayerInventory

@Suppress("Unused", "MemberVisibilityCanBePrivate")

object PlayerInventorySlots {
    
    // Invalid or not found (e.g. no space in inventory)
    const val NOT_FOUND_INVALID = PlayerInventory.NOT_FOUND

    // Hotbar slots (0-8)
    const val HOTBAR_1 = 0
    const val HOTBAR_2 = 1
    const val HOTBAR_3 = 2
    const val HOTBAR_4 = 3
    const val HOTBAR_5 = 4
    const val HOTBAR_6 = 5
    const val HOTBAR_7 = 6
    const val HOTBAR_8 = 7
    const val HOTBAR_9 = 8

    // Main inventory slots (9-35)
    const val INVENTORY_ROW1_COL1 = 9
    const val INVENTORY_ROW1_COL2 = 10
    const val INVENTORY_ROW1_COL3 = 11
    const val INVENTORY_ROW1_COL4 = 12
    const val INVENTORY_ROW1_COL5 = 13
    const val INVENTORY_ROW1_COL6 = 14
    const val INVENTORY_ROW1_COL7 = 15
    const val INVENTORY_ROW1_COL8 = 16
    const val INVENTORY_ROW1_COL9 = 17

    const val INVENTORY_ROW2_COL1 = 18
    const val INVENTORY_ROW2_COL2 = 19
    const val INVENTORY_ROW2_COL3 = 20
    const val INVENTORY_ROW2_COL4 = 21
    const val INVENTORY_ROW2_COL5 = 22
    const val INVENTORY_ROW2_COL6 = 23
    const val INVENTORY_ROW2_COL7 = 24
    const val INVENTORY_ROW2_COL8 = 25
    const val INVENTORY_ROW2_COL9 = 26

    const val INVENTORY_ROW3_COL1 = 27
    const val INVENTORY_ROW3_COL2 = 28
    const val INVENTORY_ROW3_COL3 = 29
    const val INVENTORY_ROW3_COL4 = 30
    const val INVENTORY_ROW3_COL5 = 31
    const val INVENTORY_ROW3_COL6 = 32
    const val INVENTORY_ROW3_COL7 = 33
    const val INVENTORY_ROW3_COL8 = 34
    const val INVENTORY_ROW3_COL9 = 35

    // Armor slots (36-39)
    const val ARMOR_HELMET = 39
    const val ARMOR_CHESTPLATE = 38
    const val ARMOR_LEGGINGS = 37
    const val ARMOR_BOOTS = 36

    // Offhand slot (40)
    const val OFFHAND = 40
    
}