package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.archer.ui.ArcherImGui
import imgui.ImGui
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class ClickGuiScreen: Screen(Text.literal("Click GUI")) {

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        
        ArcherImGui.render {
            ImGui.begin("ClickGUI")
            
            ImGui.text("Hello world")
            
            if (ImGui.beginTabBar("Categories")) {
                Module.Category.entries.forEach { category: Module.Category ->
                    if (ImGui.beginTabItem(category.name.lowercase())) {
                        renderCategoryContent(category)
                        ImGui.endTabItem()
                    }
                }
                ImGui.endTabBar()
            }
            
            ImGui.end()
        }
    }

    private fun renderCategoryContent(category: Module.Category) {
        val modules = ModuleHandler.fromCategory(category)

        modules.forEach { module ->
            ImGui.text(module.name)

            val enabled = module.enabled
            if (ImGui.checkbox("Enabled", enabled)) {
                module.enabled = !enabled
            }

            ImGui.separator()
        }
    }
    
}