package com.github.meo209.archer.ui.impl.elements

import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.ui.impl.ClickGuiElement
import imgui.ImGui.*
import imgui.internal.flag.ImGuiItemFlags
import imgui.type.ImInt
import kotlinx.atomicfu.AtomicRef

class BooleanElement : ClickGuiElement<Boolean> {

    override fun draw(ref: AtomicRef<Boolean>, property: ModuleProperty<Boolean>) {
        if (checkbox(property.name, ref.value)) {
            ref.value = !ref.value
        }
    }

}