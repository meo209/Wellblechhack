/*
 * This file is part of Archer (https://github.com/meo209/Archer)
 * Copyright (c) 2024 - meo209
 *
 * Archer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Archer program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.meo209.archer.ui

import imgui.ImGui
import imgui.ImVec2
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

/**
 * Implements ImGui peripheral events.
 * ImGui hooks cannot be used since they conflict with minecraft.
 * @see MinecraftImGuiImpl
 */
open class ImScreen(title: Text) : Screen(title) {

    private val data = ImGui.getIO()
    
    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        data.mousePos = ImVec2(mouseX.toFloat(), mouseY.toFloat())
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        when (button) {
            0 -> data.mouseDown = booleanArrayOf(true)
            1 -> data.mouseDown = booleanArrayOf(false, true)
            2 -> data.mouseDown = booleanArrayOf(false, false, true)
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }
    
}