package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Slider
import com.github.meo209.archer.ui.ArcherImGui
import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImDouble
import imgui.type.ImFloat
import imgui.type.ImInt
import imgui.type.ImString
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import java.awt.Color
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

class ClickGuiScreen : Screen(Text.literal("Click Gui")) {

    private var selectedCategory: Module.Category? = null
    private var selectedModule: Module? = null
    private val searchText = ImString(256)
    private var selectingKeybind: Keybind? = null // Track which keybind is being selected

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        ArcherImGui.draw {
            ImGui.begin("ClickGUI", ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoResize)

            val separation = 250f

            ImGui.setWindowPos(ImVec2(separation / 2, separation / 2))
            ImGui.setWindowSize(ImVec2(client!!.window.width - separation, client!!.window.height - separation))

            ImGui.inputTextWithHint("Search", "Module", searchText)

            if (ImGui.beginTabBar("Categories")) {
                Module.Category.entries.forEach { category: Module.Category ->
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

            ImGui.columns(2, "ModuleListAndSettings", true)

            if (selectedCategory != null) {
                ImGui.beginChild("ModuleList", ImGui.getColumnWidth() - 10, 0f, true)
                renderModuleList(selectedCategory!!, searchText.get())
                ImGui.endChild()
            }

            ImGui.nextColumn()
            if (selectedModule != null) {
                ImGui.beginChild("ModuleSettings", 0f, 0f, true)
                renderModuleSettings(selectedModule!!)
                ImGui.endChild()
            }

            ImGui.columns(1)

            ImGui.end()
        }
    }

    private fun renderModuleList(category: Module.Category, searchQuery: String) {
        val modules = ModuleHandler.fromCategory(category)

        val filteredModules = if (searchQuery.isBlank()) {
            modules
        } else {
            modules.filter { module ->
                module.name.contains(searchQuery, ignoreCase = true)
            }
        }

        filteredModules.forEach { module ->
            if (ImGui.selectable(module.name, module == selectedModule)) {
                selectedModule = module // Update the selected module
            }
        }
    }

    private fun renderModuleSettings(module: Module) {
        ImGui.text("Settings for ${module.name}")

        module::class.memberProperties.forEach { property ->
            if (property is KMutableProperty<*>) {
                when (val value = property.getter.call(module)) {
                    is Boolean -> {
                        if (ImGui.checkbox(property.name, value)) {
                            property.setter.call(module, !value)
                        }
                    }
                    is Int -> {
                        val currentValue = ImInt(value)
                        if (ImGui.inputInt(property.name, currentValue)) {
                            property.setter.call(module, currentValue.get())
                        }
                    }
                    is Float -> {
                        val currentValue = ImFloat(value)
                        if (ImGui.inputFloat(property.name, currentValue)) {
                            property.setter.call(module, currentValue.get())
                        }
                    }
                    is Double -> {
                        val currentValue = ImDouble(value)
                        if (ImGui.inputDouble(property.name, currentValue)) {
                            property.setter.call(module, currentValue.get())
                        }
                    }
                    is String -> {
                        val currentValue = ImString(value, 256)
                        if (ImGui.inputText(property.name, currentValue)) {
                            property.setter.call(module, currentValue.get())
                        }
                    }
                    is Color -> {
                        val colorArray = floatArrayOf(
                            value.red / 255f,
                            value.green / 255f,
                            value.blue / 255f,
                            value.alpha / 255f
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
                    is Slider -> {
                        // Slider setting: slider
                        val currentValue = ImFloat(value.value)
                        if (ImGui.sliderFloat(property.name, currentValue.data, value.min, value.max)) {
                            value.value = currentValue.get()
                        }
                    }
                    is Keybind -> {
                        if (ImGui.button("Keybind: ${if (value.key == -1) "None" else ImGui.getKeyName(value.key)?.uppercase()}")) {
                            selectingKeybind = value
                        }
                    }
                }
            }
        }
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (selectingKeybind != null) {
            selectingKeybind?.key = keyCode
            selectingKeybind = null
            return true
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }
}