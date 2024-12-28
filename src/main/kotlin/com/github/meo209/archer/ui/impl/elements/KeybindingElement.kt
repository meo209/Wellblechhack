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
            text("Press any key... (ESC+Shift to cancel)")
            if (lastCaptured.first != -1) {
                if (lastCaptured.first == GLFW.GLFW_KEY_GRAVE_ACCENT) {
                    ref.value.key = -1
                    reset()
                } else {
                    ref.value.key = lastCaptured.first
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