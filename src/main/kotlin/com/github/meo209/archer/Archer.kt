package com.github.meo209.archer

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.keventbus.EventBus
import org.apache.logging.log4j.LogManager

object Archer {

    private val logger = LogManager.getLogger("Archer")

    init {
        logger.info("Initializing archer...")
        FileHandler.init()
        ModuleHandler.init()

        synchronized(this) {
            EventBus.global().post(ClientStartEvent())
        }

        logger.info("Archer initialized.")
    }

    // Statically load Archer
    fun init() {}

}
