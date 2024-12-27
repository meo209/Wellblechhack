package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.ModuleProperty
import imgui.ImGui.*
import kotlinx.atomicfu.AtomicRef

class BooleanElement: ClickGuiElement<Boolean> {

    override fun draw(ref: AtomicRef<Boolean>, property: ModuleProperty<Boolean>) {
        if (checkbox(property.name, ref.value)) {
            ref.value = !ref.value
        }
    }
    
}