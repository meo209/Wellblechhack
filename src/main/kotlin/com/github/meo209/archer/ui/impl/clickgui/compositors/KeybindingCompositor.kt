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

package com.github.meo209.archer.ui.impl.clickgui.compositors

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.config.parameter.Parameter
import com.github.meo209.archer.features.module.config.parameter.ParameterType
import com.github.meo209.archer.ui.impl.clickgui.ParameterCompositor
import com.github.meo209.keventbus.EventBus
import imgui.ImGui.*
import org.lwjgl.glfw.GLFW
import oshi.hardware.PowerSource.CapacityUnits

class KeybindingCompositor : ParameterCompositor<Int>(ParameterType.KEYBINDING) {

    // Last captured key code
    private var lastCaptured = -1
    // Is currently capturing keys
    private var capturing = false
    
    init {
        EventBus.global().handler(KeyPressEvent::class, {
            lastCaptured = it.key
        }, { capturing })
    }
    
    override fun render(parameter: Parameter<Int>) {
        val keyDisplay = if (parameter.value == -1) "None" else getKeyName(parameter.value) ?: "None"
        
        if (button(keyDisplay))
            capturing = true
        
        if (capturing) {
            text("Press any key (^ to cancel)")
            if (lastCaptured != -1) {
                if (lastCaptured == GLFW.GLFW_KEY_GRAVE_ACCENT)
                    reset(parameter)
                else { 
                    parameter.value = lastCaptured
                    reset()
                }
            }
        }
    }
    
    // Reset the local vars and param to their original state
    private fun reset(param: Parameter<Int>? = null) {
        lastCaptured = -1
        capturing = false
        
        if (param != null)
            param.value = -1
    }

}