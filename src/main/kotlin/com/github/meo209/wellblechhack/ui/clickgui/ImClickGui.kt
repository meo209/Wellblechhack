/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.meo209.wellblechhack.ui.clickgui

import com.github.meo209.wellblechhack.features.module.Category
import com.github.meo209.wellblechhack.features.module.Module
import com.github.meo209.wellblechhack.features.module.ModuleManager
import com.github.meo209.wellblechhack.ui.ImScreen
import com.github.meo209.wellblechhack.ui.MinecraftImGuiImpl
import imgui.ImGui.*
import imgui.type.ImString
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

            ModuleManager.fromCategory(selectedCategory).forEach { module: Module ->
                if (selectable(module.name, selectedModule == module))
                    selectedModule = module
            }

            nextColumn()

            if (selectedModule != null && selectedModule!!.category == selectedCategory) {
                selectedModule!!.settings.forEach { setting ->
                    val compositor = ECompositor.get(setting.type)

                    compositor.render(setting)
                }
            }

            end()
        }
    }

}