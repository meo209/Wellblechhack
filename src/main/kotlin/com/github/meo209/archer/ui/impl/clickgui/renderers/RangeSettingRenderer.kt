package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.settings.Range
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import imgui.type.*
import kotlinx.atomicfu.AtomicRef

class RangeSettingRenderer : SettingRenderer<Range> {

    override fun render(ref: AtomicRef<Range>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ImFloat(ref.value.value)
        if (ImGui.sliderFloat(name, value.data, ref.value.min, ref.value.max)) {
            ref.value = ref.value.copy(value = value.get())
        }
    }
    
}