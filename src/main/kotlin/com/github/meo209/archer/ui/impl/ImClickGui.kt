package com.github.meo209.archer.ui.impl

import com.github.meo209.archer.features.Features
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.ModuleProperty
import com.github.meo209.archer.ui.ImScreen
import com.github.meo209.archer.ui.MinecraftImGuiImpl
import imgui.ImGui.*
import imgui.type.ImString
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class ImClickGui : ImScreen(Text.literal("Click Gui")) {

    private var searchText = ImString(128)
    private var selectedCategory: Category = Category.Combat
    private var selectedModule: Module? = null

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        MinecraftImGuiImpl.draw {
            begin("Click Gui")

            inputText("Search", searchText)

            if (beginTabBar("Categories")) {
                Category.entries.forEach { category: Category ->
                    if (beginTabItem(category.name)) {
                        selectedCategory = category
                        endTabItem()
                    }
                }

                endTabBar()
            }

            spacing()

            columns(2)

            Features.Module.fromCategory(selectedCategory).forEach { module: Module ->
                if (selectable(module.name, selectedModule == module))
                    selectedModule = module
            }

            nextColumn()

            if (selectedModule != null) {
                selectedModule!!.properties.forEach { property ->
                    val element = ElementRegistry.getElement(property)
                    val value = property.value!!

                    val ref = atomic(value)
                    element?.draw(ref, property)
                    if (value != ref.value) {
                        property.kProperty.setter.call(selectedModule!!, ref.value)
                    }
                }
            }

            end()
        }
    }

}