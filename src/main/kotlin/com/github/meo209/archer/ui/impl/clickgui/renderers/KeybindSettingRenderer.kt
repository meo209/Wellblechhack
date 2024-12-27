package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import kotlinx.atomicfu.AtomicRef
import kotlin.reflect.KMutableProperty

class KeybindSettingRenderer : SettingRenderer<Keybind> {

    override fun render(ref: AtomicRef<Keybind>, name: String, module: Module, screen: ClickGuiScreen) {
        val keybindText = if (ref.value.key == -1) "None" else ImGui.getKeyName(ref.value.key)?.uppercase()
        if (ImGui.button("Keybind: $keybindText")) {
            screen.selectingKeybind = ref.value
        }
    }
    
}