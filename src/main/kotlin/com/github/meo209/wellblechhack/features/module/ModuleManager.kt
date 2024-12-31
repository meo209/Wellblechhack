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

package com.github.meo209.wellblechhack.features.module

import com.github.meo209.wellblechhack.events.ClientShutdownEvent
import com.github.meo209.wellblechhack.features.module.modules.combat.ModAutoTotem
import com.github.meo209.wellblechhack.features.module.modules.`fun`.ModHomoDance
import com.github.meo209.wellblechhack.features.module.modules.uncategorized.ModClickGui
import com.github.meo209.wellblechhack.features.module.modules.uncategorized.ModDebug
import com.github.meo209.wellblechhack.features.module.modules.uncategorized.ModTest
import com.github.meo209.keventbus.EventBus
import kotlin.reflect.KClass

object ModuleManager {

    val modules = listOf(
        ModAutoTotem,
        ModDebug,
        ModClickGui,
        ModTest,
        ModHomoDance
    )

    fun fromCategory(category: Category) =
        modules.filter { it.category == category }

    inline fun <reified T : Module> get(kClass: KClass<*>): T? =
        modules.firstOrNull { it::class == kClass } as T?

    fun init() {
        ModuleSerialization.deserialize()
        modules.forEach(Module::init)

        EventBus.global().handler(ClientShutdownEvent::class, {
            modules.forEach(Module::stop)

            ModuleSerialization.serialize()
        })
    }
}