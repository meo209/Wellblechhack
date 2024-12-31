/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
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

package com.github.meo209.wellblechhack.utils

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import java.util.function.Predicate

class ItemSelectionStrategy {
    
    /**
     * Selects the best item from the inventory based on the given criteria.
     *
     * @param inventory The inventory to search through.
     * @param predicate The condition that the item must satisfy.
     * @param comparator The comparator to determine the best item.
     * @return The best item stack that matches the criteria, or null if none found.
     */
    fun selectBestItem(
        inventory: DefaultedList<ItemStack?>,
        predicate: Predicate<ItemStack?>?,
        comparator: Comparator<ItemStack?>?
    ): ItemStack? {
        return inventory.stream()
            .filter(predicate)
            .max(comparator)
            .orElse(null)
    }

    /**
     * Selects the first item that matches the given predicate.
     *
     * @param inventory The inventory to search through.
     * @param predicate The condition that the item must satisfy.
     * @return The first item stack that matches the criteria, or null if none found.
     */
    fun selectFirstItem(inventory: DefaultedList<ItemStack?>, predicate: Predicate<ItemStack?>?): ItemStack? {
        return inventory.stream()
            .filter(predicate)
            .findFirst()
            .orElse(null)
    }

    /**
     * Selects the worst item from the inventory based on the given criteria.
     * Useful for cleaning up inventory by removing the least valuable items.
     *
     * @param inventory The inventory to search through.
     * @param predicate The condition that the item must satisfy.
     * @param comparator The comparator to determine the worst item.
     * @return The worst item stack that matches the criteria, or null if none found.
     */
    fun selectWorstItem(
        inventory: DefaultedList<ItemStack?>,
        predicate: Predicate<ItemStack?>?,
        comparator: Comparator<ItemStack?>?
    ): ItemStack? {
        return inventory.stream()
            .filter(predicate)
            .min(comparator)
            .orElse(null)
    }
}