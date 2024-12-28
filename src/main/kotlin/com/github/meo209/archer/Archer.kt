/*
 * This file is part of Archer (https://github.com/meo209/Archer)
 * Copyright (c) 2024 - meo209
 *
 * Archer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Archer program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.meo209.archer

import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.events.WorldRenderEvent
import com.github.meo209.archer.features.Features
import com.github.meo209.archer.ui.MinecraftImGuiImpl
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
        
        // Modules use imgui so we init it here
        MinecraftImGuiImpl.init()
        
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

        logger.info("Archer initialized.")
    }

    // Statically load Archer
    fun init() {}

}
