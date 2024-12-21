package com.github.meo209.archer.features.gui.moduleinterface

import com.github.meo209.archer.features.module.Module
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class ModuleInterfaceScreen : Screen(Text.literal("MI")) {

    private val panels: List<Panel>

    init {
        var x = 20.0
        panels = Module.Category.entries.map { Panel(it.name, x, 20.0, 100.0, 18.0).also { x += 110.0 } }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        panels.forEach { it.render(context, mouseX, mouseY, delta) }
    }

}