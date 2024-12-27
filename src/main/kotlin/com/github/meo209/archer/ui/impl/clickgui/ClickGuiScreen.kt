package com.github.meo209.archer.ui.impl.clickgui


import com.github.meo209.archer.features.Features
import com.github.meo209.archer.features.common.Tooltip
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.ui.MinecraftImGuiImpl
import com.github.meo209.archer.ui.ImGuiScreen
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.*
import kotlinx.atomicfu.atomic
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class ClickGuiScreen : ImGuiScreen(Text.literal("Click Gui")) {

    private var selectedCategory: Category? = null
    private var selectedModule: Module? = null
    private val searchText = ImString(256)
    var selectingKeybind: Keybind? = null

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

    private fun renderSetting(property: KMutableProperty<*>, module: Module) {
        if (!property.hasAnnotation<ClickGui>()) return

        val instance = property.getter.call(module)
        val atomic = atomic(instance)

        (SettingRendererFactory.getRenderer(atomic) as? SettingRenderer<Any?>)?.render(
            atomic,
            property.name,
            module,
            this
        )

        // Check if the value has changed
        if (atomic.value != instance) {
            // Update the original property with the new value
            property.setter.call(module, atomic.value)
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

    private fun hasTooltip(property: KMutableProperty<*>): Boolean =
        property.annotations.any { it.annotationClass == Tooltip::class }

    private fun getTooltip(property: KMutableProperty<*>): String? {
        return property.annotations.find { it.annotationClass == Tooltip::class }?.let {
            (it as Tooltip).value
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