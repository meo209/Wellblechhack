package com.github.meo209.archer.features.module

import com.github.meo209.archer.features.module.modules.DebugModule
import com.github.meo209.archer.features.module.modules.HudConfigurationModule
import com.github.meo209.archer.features.module.modules.ui.HudModule
import com.github.meo209.archer.features.module.modules.ui.impl.WatermarkModule
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass

object ModuleHandler {

    private val logger = LogManager.getLogger(this)

    private val modules = arrayListOf(
        DebugModule(),
        WatermarkModule(),
        HudConfigurationModule()
    )

    init {
        logger.info("Modules loaded statically via init method")
        logger.debug(modules)
    }

    fun all() = modules.toList()

    fun hud() = modules.filterIsInstance<HudModule>()

    fun filterByName(name: String): Module? =
        modules.firstOrNull { it.name == name }

    @Suppress("UNCHECKED_CAST")
    fun <T: Module> filterByClass(clazz: KClass<T>): T? =
        modules.firstOrNull { it.javaClass.kotlin == clazz } as T?

    // Statically load the ModuleHandler
    fun init() {}

}