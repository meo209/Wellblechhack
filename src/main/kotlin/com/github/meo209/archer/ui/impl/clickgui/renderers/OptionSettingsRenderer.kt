package com.github.meo209.archer.ui.impl.clickgui.renderers

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.Option
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.archer.ui.impl.clickgui.SettingRenderer
import imgui.ImGui
import kotlinx.atomicfu.AtomicRef
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class OptionSettingsRenderer : SettingRenderer<KClass<*>> {

    override fun render(ref: AtomicRef<KClass<*>>, name: String, module: Module, screen: ClickGuiScreen) {
        // Check if the type has the @Option annotation
        println(name)
        val optionAnnotation = ref.value.findAnnotation<Option>()
        if (optionAnnotation == null) return

        // Get the list of options from the @Option annotation
        val options = optionAnnotation.value.map { it.simpleName ?: it.toString() }
        val currentValue = ref.value.simpleName ?: ref.value.toString()

        // Render a combo box for selecting the option
        if (ImGui.beginCombo(name, currentValue)) {
            options.forEach { option ->
                val isSelected = option == currentValue
                if (ImGui.selectable(option, isSelected)) {
                    // Update the value when a new option is selected
                    val selectedClass = optionAnnotation.value.find { it.simpleName == option }
                    if (selectedClass != null) {
                        ref.value = selectedClass
                    }
                }
                if (isSelected) {
                    ImGui.setItemDefaultFocus()
                }
            }
            ImGui.endCombo()
        }
    }
}