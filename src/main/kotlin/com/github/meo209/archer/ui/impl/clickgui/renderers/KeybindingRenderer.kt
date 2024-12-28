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

package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.config.types.KeybindingConfigurable
import com.github.meo209.archer.ui.impl.clickgui.ConfigurableRenderer
import com.github.meo209.keventbus.EventBus
import imgui.ImGui.*
import org.lwjgl.glfw.GLFW

class KeybindingRenderer : ConfigurableRenderer<KeybindingConfigurable> {

    // keycode
    private var lastCaptured = -1
    private var capturing = false

    init {
        EventBus.global().handler(KeyPressEvent::class, {
            lastCaptured = it.key
        }, { capturing })
    }
    
    override fun render(configurable: KeybindingConfigurable) {
        val keyName = if (configurable.value.key == -1) "None" else getKeyName(configurable.value.key) ?: "None"
        
        if (button(keyName)) {
            capturing = true
        }
        
        if (capturing) {
            text("Press any key... (^ to cancel)")
            if (lastCaptured != -1) {
                if (lastCaptured == GLFW.GLFW_KEY_GRAVE_ACCENT) 
                    reset(configurable)
                else {
                    configurable.value.key = lastCaptured
                    reset()
                }
            }
        }
    }

    private fun reset(configurable: KeybindingConfigurable? = null) {
        lastCaptured = -1
        capturing = false
        
        if (configurable != null)
            configurable.value.key = -1
    }

}