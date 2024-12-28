package com.github.meo209.archer.ui.impl.elements

import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.ui.impl.ClickGuiElement
import imgui.ImGui.*
import imgui.type.ImInt
import kotlinx.atomicfu.AtomicRef

class IntElement: ClickGuiElement<Int> {

    override fun draw(ref: AtomicRef<Int>, property: ModuleProperty<Int>) {
        val int = ImInt(ref.value)
        if (inputInt(property.name, int)) {
            ref.value = int.get()
        }
    }
    
}