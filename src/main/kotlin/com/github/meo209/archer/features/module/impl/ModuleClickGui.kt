package com.github.meo209.archer.features.module.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.common.Ignore
import com.github.meo209.archer.features.common.Tooltip
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.ImClickGui
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.gui.screen.Screen
import org.lwjgl.glfw.GLFW

class ModuleClickGui : Module("ClickGui", Category.Combat) {

    @get:JsonProperty
    var keybind by keybind("Keybind", GLFW.GLFW_KEY_RIGHT_SHIFT)

    @property:Ignore
    override var enabled: Boolean = true

    companion object {
        val screen = ImClickGui()
    }

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, {
            client.setScreen(screen)
        }, { it.key == keybind.key })
    }
}