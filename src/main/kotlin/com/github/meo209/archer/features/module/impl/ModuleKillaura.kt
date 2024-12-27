package com.github.meo209.archer.features.module.impl

import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.HudRenderEvent
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Category
import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Range
import com.github.meo209.archer.utils.math.RotationUtil
import com.github.meo209.archer.utils.math.RotationUtil.applyTo
import com.github.meo209.keventbus.EventBus
import net.minecraft.entity.LivingEntity

class ModuleKillaura : Module("Killaura", Category.Combat) {

    @property:ClickGui
    var keybind = Keybind()
    @property:ClickGui
    var range = Range(4f, 0.1f..12f)

    override fun init() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind.key })

        EventBus.global().function<ClientTickEvent>(::onTick) { enabled && inGame }
        EventBus.global().function<HudRenderEvent>(::onRenderTick) { enabled && inGame }
    }

    private fun findTarget(): LivingEntity? {
        return world!!.entities
            .asSequence()
            .filterIsInstance<LivingEntity>()
            .filterNot { it == player }
            .filterNot { player!!.distanceTo(it) > range() }
            .filter { it.isAlive }
            .minByOrNull { player!!.distanceTo(it) }
    }

    private var target: LivingEntity? = null

    private fun onRenderTick(event: HudRenderEvent) {
        if (target == null) return
        
        RotationUtil.face(target!!.pos).applyTo(player!!)
    }

    private fun onTick(event: ClientTickEvent) {
        target = findTarget() ?: return

        if (target?.isAlive == false) {
            // Invalidate target
            target == null
            return
        }

    }
}