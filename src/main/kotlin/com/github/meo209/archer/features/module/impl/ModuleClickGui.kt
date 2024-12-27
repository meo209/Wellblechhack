package com.github.meo209.archer.features.module.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.events.S2CPacketEvent
import com.github.meo209.archer.features.common.Tooltip
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.ui.impl.ImClickGui
import com.github.meo209.archer.utils.InventoryUtils
import com.github.meo209.archer.utils.PlayerInventorySlots
import com.github.meo209.keventbus.EventBus
import net.minecraft.entity.EntityStatuses
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
import org.lwjgl.glfw.GLFW

class ModuleClickGui : Module("ClickGui", Category.Combat) {

    @get:JsonProperty
    var keybind by keybind("Keybind", GLFW.GLFW_KEY_RIGHT_SHIFT)

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { 
            client.setScreen(ImClickGui())
        }, { it.key == keybind })
    }

}