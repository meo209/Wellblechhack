package com.github.meo209.archer.events

import com.github.meo209.keventbus.Event
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack

class HudRenderEvent(val context: DrawContext): Event
class WorldRenderEvent(val matrixStack: MatrixStack): Event