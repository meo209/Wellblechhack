package com.github.meo209.archer.ui.impl


import com.github.meo209.archer.features.Features
import com.github.meo209.archer.features.common.Tooltip
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Range
import com.github.meo209.archer.ui.MinecraftImGuiImpl
import com.github.meo209.archer.ui.ImGuiScreen
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.*
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import java.awt.Color
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

class ClickGuiScreen : ImGuiScreen(Text.literal("Click Gui")) {

    private var selectedCategory: Category? = null
    private var selectedModule: Module? = null
    private val searchText = ImString(256)
    private var selectingKeybind: Keybind? = null

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        MinecraftImGuiImpl.draw {
            ImGui.pushFont(MinecraftImGuiImpl.Fonts["Inter20"])
            renderClickGuiWindow()
            ImGui.popFont()
        }
    }

    private fun renderClickGuiWindow() {
        ImGui.begin("ClickGUI", ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoTitleBar)

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
            Category.entries.forEach { category ->
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

    private fun renderModuleList(category: Category, searchQuery: String) {
        ImGui.beginChild("ModuleList", ImGui.getColumnWidth() - 10, 0f, true)

        val modules = Features.Module.fromCategory(category)
        val filteredModules =
            if (searchQuery.isBlank()) modules else modules.filter { it.name.contains(searchQuery, true) }

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

    private fun hasTooltip(property: KMutableProperty<*>): Boolean =
        property.annotations.any { it.annotationClass == Tooltip::class }
    
    private fun getTooltip(property: KMutableProperty<*>): String? {
        return property.annotations.find { it.annotationClass == Tooltip::class }?.let {
            (it as Tooltip).value
        }
    }

    private fun renderSetting(property: KMutableProperty<*>, module: Module) {
        when (val value = property.getter.call(module)) {
            is Boolean -> renderBooleanSetting(property, module, value)
            is Int -> renderIntSetting(property, module, value)
            is Float -> renderFloatSetting(property, module, value)
            is Double -> renderDoubleSetting(property, module, value)
            is String -> renderStringSetting(property, module, value)
            is Color -> renderColorSetting(property, module, value)
            is Keybind -> renderKeybindSetting(property, module, value)
            is Range -> renderRangeSetting(property, module, value)
        }

        if (hasTooltip(property)) {
            ImGui.sameLine()
            ImGui.textDisabled("(?)")
            if (ImGui.isItemHovered()) {
                ImGui.beginTooltip()
                ImGui.pushTextWrapPos(ImGui.getFontSize() * 35.0f)
                ImGui.textUnformatted(getTooltip(property))
                ImGui.popTextWrapPos()
                ImGui.endTooltip()
            }
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

    private fun renderKeybindSetting(property: KMutableProperty<*>, module: Module, value: Keybind) {
        val keybindText = if (value.key == -1) "None" else ImGui.getKeyName(value.key)?.uppercase()
        if (ImGui.button("Keybind: $keybindText")) {
            selectingKeybind = value
        }
    }

    private fun renderRangeSetting(property: KMutableProperty<*>, module: Module, value: Range) {
        val currentValue = ImFloat(value.value)
        if (ImGui.sliderFloat(property.name, currentValue.data, value.min, value.max)) {
            property.setter.call(module, currentValue.get())
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