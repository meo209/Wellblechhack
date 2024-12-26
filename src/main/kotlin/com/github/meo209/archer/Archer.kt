package com.github.meo209.archer

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.events.WorldRenderEvent
import com.github.meo209.archer.features.Features
import com.github.meo209.archer.ui.ArcherImGui
import com.github.meo209.keventbus.EventBus
import me.x150.renderer.event.RenderEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager

object Archer {

    private val logger = LogManager.getLogger("Archer")

    object Data {
        val VERSION = "0.0.1+${MinecraftClient.getInstance().gameVersion}"
        val MOD_ID = "archer"
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
        Features.init()

        synchronized(this) {
            EventBus.global().post(ClientStartEvent())
        }

        RenderEvents.HUD.register { context ->
            EventBus.global().post(HudRenderEvent(context))
        }

        RenderEvents.WORLD.register { stack ->
            EventBus.global().post(WorldRenderEvent(stack))
        }

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            EventBus.global().post(ClientTickEvent())
        }
        
        ArcherImGui.init()

        logger.info("Archer initialized.")
    }

    // Statically load Archer
    fun init() {}

}
