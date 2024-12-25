package com.github.meo209.archer.features.module.modules

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.utils.Colors
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import net.minecraft.client.MinecraftClient


class DebugModule : Module("Debug", Category.OTHER) {

    companion object Info {
        @JsonIgnore
        val infoLines = mutableMapOf<String, String>()
    }

    override fun register() {
        EventBus.global().function<HudRenderEvent>(::renderHud) { enabled }
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