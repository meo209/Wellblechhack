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
open class ImGuiScreen(title: Text) : Screen(title) {

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