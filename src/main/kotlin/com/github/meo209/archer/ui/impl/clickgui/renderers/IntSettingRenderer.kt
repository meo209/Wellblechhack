package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import imgui.type.ImInt
import kotlinx.atomicfu.AtomicRef

class IntSettingRenderer : SettingRenderer<Int> {

    override fun render(ref: AtomicRef<Int>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ImInt(ref.value)
        if (ImGui.inputInt(name, value)) {
            ref.value = value.get()
        }
    }
    
}