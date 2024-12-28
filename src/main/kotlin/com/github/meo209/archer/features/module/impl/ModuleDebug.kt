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


package com.github.meo209.archer.features.module.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

class ModuleDebug : Module("Debug", Category.Uncategorized) {
    
    companion object {
        private val infoLines = mutableSetOf<String>()

        fun info(content: String?) {
            content?.let { infoLines.add(it) } 
        }
        
        fun remove(content: String?) {
            content?.let { infoLines.remove(it) }
        }
        
        fun clear() {
            infoLines.clear()
        }
    }

    override fun init() {
        EventBus.global().handler(HudRenderEvent::class, { event ->
            var offset = 42
            infoLines.forEach { content ->
                event.context.drawCenteredTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    content,
                    client.window.width / 4,
                    offset,
                    -1
                )
                offset += MinecraftClient.getInstance().textRenderer.fontHeight + 2
            }
        }, { enabled })
    }
}