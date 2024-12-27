package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import kotlinx.atomicfu.AtomicRef

class BooleanSettingRenderer : SettingRenderer<Boolean> {

    override fun render(ref: AtomicRef<Boolean>, name: String, module: Module, screen: ClickGuiScreen) {
        if (ImGui.checkbox(name, ref.value)) {
            ref.value = !ref.value
        }
    }
    
}