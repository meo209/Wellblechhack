package com.github.meo209.archer.features.module

import com.github.meo209.archer.events.ClientShutdownEvent
import com.github.meo209.archer.features.module.impl.*
import com.github.meo209.archer.features.module.impl.killaura.ModuleKillaura
import com.github.meo209.keventbus.EventBus

class ModuleFeature {

    private val modules = arrayListOf(
        ModuleClickGui(),
        ModuleAutoTotem(),
        ModuleDebug(),
        ModuleKillaura(),
    )


    fun all() = modules.toList()

    fun fromCategory(category: Category) =
        all().filter { it.category == category }

    init {
        modules.forEach(ModuleIO::load)
        modules.forEach(Module::init)

        EventBus.global().handler(ClientShutdownEvent::class, {
            modules.forEach(Module::stop)
        })
    }
}