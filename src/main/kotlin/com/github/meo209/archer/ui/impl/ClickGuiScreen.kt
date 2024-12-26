package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.archer.features.module.Setting
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Slider
import com.github.meo209.archer.ui.ArcherImGui
import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiWindowFlags
import imgui.type.*
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.awt.Color
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class ClickGuiScreen : Screen(Text.literal("Click Gui")) {

    private var selectedCategory: Module.Category? = null
    private var selectedModule: Module? = null
    private val searchText = ImString(256)
    private var selectingKeybind: Keybind? = null

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        ArcherImGui.draw {
            renderClickGuiWindow()
        }
    }

    private fun renderClickGuiWindow() {
        ImGui.begin("ClickGUI", ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoResize)

        val separation = 250f
        ImGui.setWindowPos(ImVec2(separation / 2, separation / 2))
        ImGui.setWindowSize(ImVec2(client!!.window.width - separation, client!!.window.height - separation))

        renderSearchBar()
        renderCategoryTabs()
        renderModuleListAndSettings()

        ImGui.end()
    }

    private fun renderSearchBar() {
        ImGui.inputTextWithHint("Search", "Module", searchText)
    }

    private fun renderCategoryTabs() {
        if (ImGui.beginTabBar("Categories")) {
            Module.Category.entries.forEach { category ->
                if (ImGui.beginTabItem(category.name.lowercase())) {
                    if (selectedCategory != category) {
                        selectedCategory = category
                        selectedModule = null
                    }
                    ImGui.endTabItem()
                }
            }
            ImGui.endTabBar()
        }
    }

    private fun renderModuleListAndSettings() {
        ImGui.columns(2, "ModuleListAndSettings", true)

        selectedCategory?.let { category ->
            renderModuleList(category, searchText.get())
        }

        ImGui.nextColumn()

        selectedModule?.let { module ->
            renderModuleSettings(module)
        }

        ImGui.columns(1)
    }

    private fun renderModuleList(category: Module.Category, searchQuery: String) {
        ImGui.beginChild("ModuleList", ImGui.getColumnWidth() - 10, 0f, true)

        val modules = ModuleHandler.fromCategory(category)
        val filteredModules = if (searchQuery.isBlank()) modules else modules.filter { it.name.contains(searchQuery, true) }

        filteredModules.forEach { module ->
            if (ImGui.selectable(module.name, module == selectedModule)) {
                selectedModule = module
            }
        }

        ImGui.endChild()
    }

    private fun renderModuleSettings(module: Module) {
        ImGui.beginChild("ModuleSettings", 0f, 0f, true)
        ImGui.text("Settings for ${module.name}")

        module::class.memberProperties
            .filterIsInstance<KMutableProperty<*>>()
            .forEach { property -> renderSetting(property, module) }

        ImGui.endChild()
    }

    private fun renderSetting(property: KMutableProperty<*>, module: Module) {
        when (val value = property.getter.call(module)) {
            is Boolean -> renderBooleanSetting(property, module, value)
            is Int -> renderIntSetting(property, module, value)
            is Float -> renderFloatSetting(property, module, value)
            is Double -> renderDoubleSetting(property, module, value)
            is String -> renderStringSetting(property, module, value)
            is Color -> renderColorSetting(property, module, value)
            is Slider -> renderSliderSetting(property, module, value)
            is Keybind -> renderKeybindSetting(property, module, value)
        }
    }

    private fun renderBooleanSetting(property: KMutableProperty<*>, module: Module, value: Boolean) {
        if (ImGui.checkbox(property.name, value)) {
            property.setter.call(module, !value)
        }
    }

    private fun renderIntSetting(property: KMutableProperty<*>, module: Module, value: Int) {
        val currentValue = ImInt(value)
        if (ImGui.inputInt(property.name, currentValue)) {
            property.setter.call(module, currentValue.get())
        }
    }

    private fun renderFloatSetting(property: KMutableProperty<*>, module: Module, value: Float) {
        val currentValue = ImFloat(value)
        if (ImGui.inputFloat(property.name, currentValue)) {
            property.setter.call(module, currentValue.get())
        }
    }

    private fun renderDoubleSetting(property: KMutableProperty<*>, module: Module, value: Double) {
        val currentValue = ImDouble(value)
        if (ImGui.inputDouble(property.name, currentValue)) {
            property.setter.call(module, currentValue.get())
        }
    }

    private fun renderStringSetting(property: KMutableProperty<*>, module: Module, value: String) {
        val currentValue = ImString(value, 256)
        if (ImGui.inputText(property.name, currentValue)) {
            property.setter.call(module, currentValue.get())
        }
    }

    private fun renderColorSetting(property: KMutableProperty<*>, module: Module, value: Color) {
        val colorArray = floatArrayOf(
            value.red / 255f, value.green / 255f, value.blue / 255f, value.alpha / 255f
        )
        if (ImGui.colorEdit4(property.name, colorArray)) {
            val newColor = Color(
                (colorArray[0] * 255).toInt(),
                (colorArray[1] * 255).toInt(),
                (colorArray[2] * 255).toInt(),
                (colorArray[3] * 255).toInt()
            )
            property.setter.call(module, newColor)
        }
    }

    private fun renderSliderSetting(property: KMutableProperty<*>, module: Module, value: Slider) {
        val currentValue = ImFloat(value.value)
        if (ImGui.sliderFloat(property.name, currentValue.data, value.min, value.max)) {
            value.value = currentValue.get()
        }
    }

    private fun renderKeybindSetting(property: KMutableProperty<*>, module: Module, value: Keybind) {
        val keybindText = if (value.key == -1) "None" else ImGui.getKeyName(value.key)?.uppercase() ?: "Unknown"
        if (ImGui.button("Keybind: $keybindText")) {
            selectingKeybind = value
        }
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        selectingKeybind?.let {
            it.key = keyCode
            selectingKeybind = null
            return true
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }
}