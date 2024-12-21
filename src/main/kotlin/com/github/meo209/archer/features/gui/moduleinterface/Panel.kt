package com.github.meo209.archer.features.gui.moduleinterface

import com.github.meo209.archer.ColorPalette
import com.github.meo209.archer.color
import me.x150.renderer.render.Renderer2d
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable

class Panel(val title: String, var x: Double, var y: Double, var width: Double, var height: Double): Drawable {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        Renderer2d.renderRoundedQuad(context.matrices, ColorPalette.PASTEL_DARK.DARK_PURPLE.color(),
            x, y, x + width, y + height, 2f, 10f)

        val centerX = x + width / 2

        context.drawText(MinecraftClient.getInstance().textRenderer, title, (centerX - width / 8).toInt(), y.toInt(), ColorPalette.PASTEL_DARK.AQUAMARINE, false)
    }

}