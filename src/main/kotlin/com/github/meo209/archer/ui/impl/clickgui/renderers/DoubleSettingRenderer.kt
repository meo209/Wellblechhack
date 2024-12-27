package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import imgui.type.ImDouble
import kotlinx.atomicfu.AtomicRef

class DoubleSettingRenderer : SettingRenderer<Double> {

    override fun render(ref: AtomicRef<Double>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ImDouble(ref.value)
        if (ImGui.inputDouble(name, value)) {
            ref.value = value.get()
        }
    }
    
}