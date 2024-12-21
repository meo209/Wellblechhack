package com.github.meo209.archer.features.module.ui.impl

import com.github.meo209.archer.Archer
import com.github.meo209.archer.features.module.ui.HudModule
import com.github.meo209.archer.utils.math.Vec2
import me.x150.renderer.render.Renderer2d
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class WatermarkModule : HudModule("Watermark", Category.RENDER) {

    private val identifier = Identifier.of("archer", "icon.png")

    override fun calculateSize(): Vec2 = Vec2(24.0, 24.0)

    override fun render(context: DrawContext) {
        Renderer2d.renderTexture(
            context.matrices,
            identifier,
            position.x,
            position.y,
            size.x,
            size.y
        )

        context.drawText(
            MinecraftClient.getInstance().textRenderer,
            Text.literal("[${Archer.Data.VERSION}]"),
            (position.x + 5).toInt(),
            (position.y + size.y + 5).toInt(), -1, false
        )
    }

    override fun renderConfiguration(context: DrawContext, mouseX: Double, mouseY: Double, delta: Float) {
        render(context)
    }
}