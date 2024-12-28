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

import com.github.meo209.archer.events.ClientShutdownEvent
import com.github.meo209.archer.features.module.impl.*
import com.github.meo209.keventbus.EventBus
import kotlin.reflect.KClass

class ModuleFeature {

    private val modules = arrayListOf(
        ModuleAutoTotem(),
        ModuleDebug(),
        ModuleClickGui(),
        ModuleTest()
    )


    fun all() = modules.toList()

    fun fromCategory(category: Category) =
        all().filter { it.category == category }
    
    inline fun <reified T: Module> get(kClass: KClass<*>): T? =
        all().firstOrNull { it::class == kClass } as T?

    init {
        modules.forEach(ModuleIO::load)
        modules.forEach(Module::init)

        EventBus.global().handler(ClientShutdownEvent::class, {
            modules.forEach(Module::stop)
        })
    }
}