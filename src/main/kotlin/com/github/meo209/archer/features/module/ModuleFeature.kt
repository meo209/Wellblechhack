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