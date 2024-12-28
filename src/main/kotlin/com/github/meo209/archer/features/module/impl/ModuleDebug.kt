
package com.github.meo209.archer.features.module.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.Module
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

class ModuleDebug : Module("Debug", Category.Uncategorized) {

    @get:JsonProperty
    val test by range("Test", 0f, 0f..10f)

    companion object {
        private val infoLines = mutableSetOf<String>()

        fun info(content: String?) {
            content?.let { infoLines.add(it) } 
        }
        
        fun remove(content: String?) {
            content?.let { infoLines.remove(it) }
        }
        
        fun clear() {
            infoLines.clear()
        }
    }

    override fun init() {
        EventBus.global().handler(HudRenderEvent::class, { event ->
            var offset = 42
            infoLines.forEach { content ->
                event.context.drawCenteredTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    content,
                    client.window.width / 4,
                    offset,
                    -1
                )
                offset += MinecraftClient.getInstance().textRenderer.fontHeight + 2
            }
        }, { enabled })

        EventBus.global().handler(ClientTickEvent::class, { event ->
            //println("----")
            //println(player!!.inventory.getStack(PlayerInventorySlots.ARMOR_HELMET))
        }, { enabled && inGame })
    }
}