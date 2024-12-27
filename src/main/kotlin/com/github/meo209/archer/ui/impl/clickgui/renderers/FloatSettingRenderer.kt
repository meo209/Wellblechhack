package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import imgui.type.ImFloat
import kotlinx.atomicfu.AtomicRef

class FloatSettingRenderer : SettingRenderer<Float> {

    override fun render(ref: AtomicRef<Float>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ImFloat(ref.value)
        if (ImGui.inputFloat(name, value)) {
            ref.value = value.get()
        }
    }
    
}