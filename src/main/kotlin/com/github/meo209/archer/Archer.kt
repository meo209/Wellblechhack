package com.github.meo209.archer

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.keventbus.EventBus
import me.x150.renderer.event.RenderEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager

object Archer {

    private val logger = LogManager.getLogger("Archer")

    object Data {
        val VERSION = "0.0.1+${MinecraftClient.getInstance().gameVersion}"
    }

    /*
    val GSON = GsonBuilder()
        //.registerTypeAdapter(Module::class.java, ModuleTypeAdapter())
        .setExclusionStrategies(ModuleExclusionStrategy())
        .setPrettyPrinting()
        .create()
     */

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

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            EventBus.global().post(ClientTickEvent())
        }

        logger.info("Archer initialized.")
    }

    // Statically load Archer
    fun init() {}

}
