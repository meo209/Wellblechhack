package com.github.meo209.archer.features.module.modules.ui.impl

import com.github.meo209.archer.Archer
import com.github.meo209.archer.features.module.modules.ui.HudModule
import me.x150.renderer.render.Renderer2d
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.joml.Vector2i

class WatermarkModule : HudModule("Watermark", Category.RENDER) {

    private val identifier = Identifier.of("archer", "icon.png")

    override fun calculateSize(): Vector2i = Vector2i(24, 24)

    override fun render(context: DrawContext) {
        Renderer2d.renderTexture(
            context.matrices,
            identifier,
            position.x.toDouble(),
            position.y.toDouble(),
            size.x.toDouble(),
            size.y.toDouble()
        )

        context.drawText(
            MinecraftClient.getInstance().textRenderer,
            Text.literal("[${Archer.Data.VERSION}]"),
            position.x + 5,
            position.y + size.y + 5, -1, false
        )
    }

    override fun renderConfiguration(context: DrawContext, mouseX: Double, mouseY: Double, delta: Float) {
        render(context)
    }
}