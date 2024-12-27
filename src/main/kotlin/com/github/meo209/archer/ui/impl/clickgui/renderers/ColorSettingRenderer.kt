package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import kotlinx.atomicfu.AtomicRef
import java.awt.Color

class ColorSettingRenderer : SettingRenderer<Color> {

    override fun render(ref: AtomicRef<Color>, name: String, module: Module, screen: ClickGuiScreen) {
        val value = ref.value
        val colorArray = floatArrayOf(
            value.red / 255f, value.green / 255f, value.blue / 255f, value.alpha / 255f
        )
        if (ImGui.colorEdit4(name, colorArray)) {
            val newColor = Color(
                (colorArray[0] * 255).toInt(),
                (colorArray[1] * 255).toInt(),
                (colorArray[2] * 255).toInt(),
                (colorArray[3] * 255).toInt()
            )
            ref.value = newColor
        }
    }
    
}