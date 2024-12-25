package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.Setting
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.ui.impl.ClickGuiScreen
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

class ClickGuiModule: Module("ClickGui", Category.RENDER) {

    @Setting
    var keybind: Keybind = Keybind(GLFW.GLFW_KEY_RIGHT_SHIFT)

    @Setting
    override var enabled = true
    
    override fun register() {
        EventBus.global().function<KeyPressEvent>(::onKey) { enabled && it.key == keybind.key }
    }

    @FunctionTarget
    private fun onKey(event: KeyPressEvent) {
        MinecraftClient.getInstance().setScreen(ClickGuiScreen())
    }
    
}