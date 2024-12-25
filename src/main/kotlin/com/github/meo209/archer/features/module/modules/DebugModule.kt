package com.github.meo209.archer.features.module.modules

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.Setting
import com.github.meo209.archer.features.module.settings.Slider
import com.github.meo209.archer.ui.impl.ClickGuiScreen
import com.github.meo209.archer.utils.Colors
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW
import java.awt.Color


class DebugModule : Module("Debug", Category.OTHER) {

    companion object Info {
        @JsonIgnore
        val infoLines = mutableMapOf<String, String>()
    }
    
    @Setting
    var key = GLFW.GLFW_KEY_RIGHT_SHIFT
    
    @Setting
    var slider = Slider(0f, 0f, 69f)
    
    @Setting
    var colorPicker = Color(0f, 0f, 0f)

    override fun register() {
        EventBus.global().function<HudRenderEvent>(::renderHud) { enabled }
        EventBus.global().function<KeyPressEvent>(::onKey) { enabled && it.key == key }
    }
    
    @FunctionTarget
    private fun onKey(event: KeyPressEvent) {
        MinecraftClient.getInstance().setScreen(ClickGuiScreen())
    }

    // Draws the info lines on the hud
    @FunctionTarget
    private fun renderHud(event: HudRenderEvent) {
        val stack = event.context
        val textRenderer = MinecraftClient.getInstance().textRenderer

        stack.drawCenteredTextWithShadow(
            textRenderer,
            infoLines.toList()
                .joinToString { "${Colors.AQUA}${it.first}${Colors.LIGHT_PURPLE}: ${Colors.WHITE}${it.second}" },
            event.context.scaledWindowWidth / 2,
            38,
            -1
        )
    }

}