package com.github.meo209.archer.features.module.impl

import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.common.Tooltip
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.ui.impl.clickgui.ClickGuiScreen
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

class ModuleClickGui : Module("ClickGui", Category.Uncategorized) {
    
    @property:ClickGui
    var keybind: Keybind = Keybind(GLFW.GLFW_KEY_RIGHT_SHIFT)

    // Lock enabled to always be true
    @property:Tooltip("Always enabled.")
    @property:ClickGui
    override var enabled: Boolean = true
        get() = true
        set(_) { field = true }
    
    override fun init() {
        EventBus.global().function<KeyPressEvent>(::onKey) { it.key == keybind.key }
    }
    
    companion object {
        val SCREEN by lazy { ClickGuiScreen() }
    }

    private fun onKey(event: KeyPressEvent) {
        MinecraftClient.getInstance().setScreen(SCREEN)
    }

}