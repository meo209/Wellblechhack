package com.github.meo209.archer.features.module

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.features.module.modules.DebugModule
import com.github.meo209.keventbus.EventBus
import org.apache.logging.log4j.LogManager

object ModuleHandler {

    private val logger = LogManager.getLogger(this)

    private val modules = arrayListOf<Module>(
        DebugModule()
    )

    init {
        logger.info("Modules loaded statically via init method")
        logger.debug(modules)
    }

    // Statically load the ModuleHandler
    fun init() {}

}