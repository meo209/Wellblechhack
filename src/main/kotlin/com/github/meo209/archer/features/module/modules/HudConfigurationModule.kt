package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.ui.HudConfigurationScreen
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

class HudConfigurationModule : Module(name = "HudConfig", Category.OTHER) {

    companion object {
        val SCREEN = HudConfigurationScreen()
    }

    override fun register() {
        EventBus.global().handler(KeyPressEvent::class) { event ->
            if (event.key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                MinecraftClient.getInstance().setScreen(SCREEN)
            }
        }
    }

}