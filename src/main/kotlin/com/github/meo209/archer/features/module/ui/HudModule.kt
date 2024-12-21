package com.github.meo209.archer.features.module.ui

import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.features.module.Include
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.utils.math.Vec2
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.gui.DrawContext

abstract class HudModule(name: String, category: Category) : Module(name, category) {

    @Include
    var position = Vec2(0.0, 0.0)

    @Include
    var size = Vec2(0.0, 0.0)

    @Include
    var visible = true

    open var resizable: Boolean = true
    open var movable: Boolean = true

    abstract fun calculateSize(): Vec2

    open fun canMoveTo(pos: Vec2): Boolean = true

    abstract fun render(context: DrawContext)

    abstract fun renderConfiguration(context: DrawContext, mouseX: Double, mouseY: Double, delta: Float)

    private fun update() {
        size = calculateSize()
    }

    init {
        EventBus.global().handler(HudRenderEvent::class) { event ->
            if (visible) {
                update()
                render(event.context)
            }
        }
    }

}

fun Module.asHud() =
    this as HudModule