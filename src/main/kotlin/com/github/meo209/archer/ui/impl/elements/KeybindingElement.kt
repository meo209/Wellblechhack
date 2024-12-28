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

package com.github.meo209.archer.ui.impl.elements

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.features.module.specific.Keybinding
import com.github.meo209.archer.ui.impl.ClickGuiElement
import com.github.meo209.keventbus.EventBus
import imgui.ImGui.*
import imgui.type.ImInt
import kotlinx.atomicfu.AtomicRef
import org.lwjgl.glfw.GLFW
import java.util.concurrent.atomic.AtomicReference

class KeybindingElement: ClickGuiElement<Keybinding> {
    
    // keycode : modifiers
    private var lastCaptured = Pair(-1, -1)
    private var capturing = false
    
    init {
        EventBus.global().handler(KeyPressEvent::class, {
            lastCaptured = Pair(it.key, it.modifiers)
        }, { capturing })
    }
    
    override fun draw(ref: AtomicRef<Keybinding>, property: ModuleProperty<Keybinding>) {
        val keyName = if (ref.value.key == -1) "None" else getKeyName(ref.value.key) ?: "None"
        if (button(keyName)) {
            capturing = true
        }

        if (capturing) {
            text("Press any key... (GraveAccent ^ to cancel)")
            if (lastCaptured.first != -1) {
                if (lastCaptured.first == GLFW.GLFW_KEY_GRAVE_ACCENT) {
                    ref.value = Keybinding(-1)
                    reset()
                } else {
                    ref.value = Keybinding(lastCaptured.first)
                    reset()
                }
            }
        }
    }
    
    private fun reset() {
        lastCaptured = Pair(-1, -1)
        capturing = false
    }
    
}