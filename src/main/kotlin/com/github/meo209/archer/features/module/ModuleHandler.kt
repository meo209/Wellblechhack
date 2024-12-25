package com.github.meo209.archer.features.module

import com.github.meo209.archer.features.module.modules.DebugModule
import org.apache.logging.log4j.LogManager

object ModuleHandler {

    private val logger = LogManager.getLogger(this)

    private val modules = arrayListOf(
        DebugModule()
    )

    init {
        logger.info("Modules loaded statically via init method")
        logger.debug(modules)
    }

    fun all() = modules.toList()
    
    fun fromCategory(category: Module.Category) =
        all().filter { it.category == category }

    // Statically load the ModuleHandler
    fun init() {}

}