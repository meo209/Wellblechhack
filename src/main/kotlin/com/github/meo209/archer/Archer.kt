package com.github.meo209.archer

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.keventbus.Event
import com.github.meo209.keventbus.EventBus
import me.x150.renderer.event.RenderEvents
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager

object Archer {

    private val logger = LogManager.getLogger("Archer")

    object Data {
        val VERSION = "0.0.1+${MinecraftClient.getInstance().gameVersion}"
    }

    init {
        logger.info("Initializing archer...")
        FileHandler.init()
        ModuleHandler.init()

        synchronized(this) {
            EventBus.global().post(ClientStartEvent())
        }

        RenderEvents.HUD.register { context ->
            EventBus.global().post(HudRenderEvent(context))
        }

        logger.info("Archer initialized.")
    }

    // Statically load Archer
    fun init() {}

}
