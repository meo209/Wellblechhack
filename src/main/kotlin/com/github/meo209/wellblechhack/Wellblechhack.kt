/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.meo209.wellblechhack

import com.github.meo209.wellblechhack.events.ClientStartEvent
import com.github.meo209.wellblechhack.events.ClientTickEvent
import com.github.meo209.wellblechhack.events.HudRenderEvent
import com.github.meo209.wellblechhack.events.WorldRenderEvent
import com.github.meo209.wellblechhack.ui.MinecraftImGuiImpl
import com.github.meo209.keventbus.EventBus
import com.github.meo209.wellblechhack.features.module.ModuleManager
import me.x150.renderer.event.RenderEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager

object Wellblechhack {

    private val logger = LogManager.getLogger(Data.MOD_ID)

    object Data {
        val VERSION = "0.0.1+${MinecraftClient.getInstance().gameVersion}"
        val MOD_ID = "Wellblechhack"
    }

    init {
        logger.info("Initializing ${Data.MOD_ID}...")
        FileHandler.init()
        
        // Modules use imgui so we init it here
        MinecraftImGuiImpl.init()

        ModuleManager.init()

        EventBus.global().post(ClientStartEvent())

        RenderEvents.HUD.register { context ->
            EventBus.global().post(HudRenderEvent(context))
        }

        RenderEvents.WORLD.register { stack ->
            EventBus.global().post(WorldRenderEvent(stack))
        }

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            EventBus.global().post(ClientTickEvent())
        }

        logger.info("${Data.MOD_ID} initialized.")
    }

    // Statically load Wellblechhack
    fun init() {}

}
