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

package com.github.meo209.archer.features.module.config.parameter

import com.github.meo209.archer.features.module.Configurable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Parameter<T>(val name: String, var value: T, val type: ParameterType) : ReadWriteProperty<Configurable, T> {

    // No-argument constructor for kryo
    // SHOULD NEVER BE USED DIRECTLY
    constructor() : this("", null as T, ParameterType.UNKNOWN)
    
    override fun setValue(thisRef: Configurable, property: KProperty<*>, value: T) {
        this.value = value
    }
        
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Configurable, property: KProperty<*>): T {
        return thisRef.parameters.first { it.name == name && it.type == type }.value as T
    }
    
    @Suppress("UNCHECKED_CAST")
    fun syncFrom(other: Parameter<*>) {
        require(this.type == other.type) { "Parameter types do not match!" }
        
        this.value = other.value as T
    }

}