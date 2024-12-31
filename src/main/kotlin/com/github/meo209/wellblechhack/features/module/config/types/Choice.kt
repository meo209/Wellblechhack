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

package com.github.meo209.wellblechhack.features.module.config.types

import com.github.meo209.wellblechhack.features.module.Configurable

/**
 * Represents a collection of the currently selected choice and all other options
 */
class Choice(
    val options: List<NamedChoice> = emptyList(), // Default to an empty list
    var current: NamedChoice = options.firstOrNull() ?: DefaultNamedChoice() // Default to the first option or a default instance
) {

}

/**
 * A default implementation of [NamedChoice] for use in the no-argument constructor
 */
private class DefaultNamedChoice : NamedChoice("default")

/**
 * An entry in [Choice]
 * @see Configurable
 */
abstract class NamedChoice(name: String) : Configurable(name)