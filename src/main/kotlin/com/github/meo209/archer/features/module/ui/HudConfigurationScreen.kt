package com.github.meo209.archer.features.module.ui

import com.github.meo209.archer.features.module.ModuleHandler
import com.github.meo209.archer.features.module.modules.HudConfigurationModule
import me.x150.renderer.render.Renderer2d
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.awt.Color

class HudConfigurationScreen : Screen(
    Text.literal("Hud Config")
) {

    private val modules
        get() = ModuleHandler.hud()

    private var currentModule: HudModule? = null

    private var enabled = mutableListOf<HudModule>()

    override fun init() {
        super.init()

        ModuleHandler.hud().filter { it.enabled }.forEach { it.visible = false }
    }

    override fun close() {
        super.close()
        ModuleHandler.hud().filter { it.enabled }.forEach { it.visible = true }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        // Render the background or other UI elements if needed
        // super.render(context, mouseX, mouseY, delta)

        modules.forEach { hudModule ->
            hudModule.renderConfiguration(context, mouseX.toDouble(), mouseY.toDouble(), delta)

            Renderer2d.renderQuad(
                context.matrices, Color(156, 156, 156, 60), hudModule.position.x, hudModule.position.y,
                hudModule.position.x + hudModule.size.x, hudModule.position.y + hudModule.size.y
            )
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        currentModule = modules.firstOrNull { isInBB(it, mouseX, mouseY) }

        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        currentModule = null
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
        currentModule?.let { module ->
            module.position.x += deltaX
            module.position.y += deltaY
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
    }

    private fun isInBB(module: HudModule, mouseX: Double, mouseY: Double): Boolean =
        mouseX >= module.position.x &&
                mouseX <= module.position.x + module.size.x &&
                mouseY >= module.position.y &&
                mouseY <= module.position.y + module.size.y
}