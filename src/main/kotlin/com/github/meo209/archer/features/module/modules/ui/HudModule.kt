package com.github.meo209.archer.features.module.modules.ui

import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.Include
import com.github.meo209.archer.features.module.Module
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.gui.DrawContext
import org.joml.Vector2i

abstract class HudModule(name: String, category: Category) : Module(name, category) {

    @Include
    var position = Vector2i(0, 0)

    @Include
    var size = Vector2i(0, 0)

    open var resizable: Boolean = true
    open var movable: Boolean = true

    abstract fun calculateSize(): Vector2i

    open fun canMoveTo(pos: Vector2i): Boolean = true

    abstract fun render(context: DrawContext)

    abstract fun renderConfiguration(context: DrawContext, mouseX: Double, mouseY: Double, delta: Float)

    private fun update() {
        size = calculateSize()
    }

    init {
        EventBus.global().handler(HudRenderEvent::class) { event ->
            update()
            render(event.context)
        }
    }

}