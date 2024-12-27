package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import imgui.type.ImString
import kotlinx.atomicfu.AtomicRef

class StringSettingRenderer : SettingRenderer<String> {

    override fun render(ref: AtomicRef<String>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ImString(ref.value, 1024)
        if (ImGui.inputText(name, value)) {
            ref.value = value.get()
        }
    }
    
}