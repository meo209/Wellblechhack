package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.events.ClientTickEvent
import com.github.meo209.archer.events.KeyPressEvent
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.Setting
import com.github.meo209.archer.features.module.settings.Keybind
import com.github.meo209.archer.features.module.settings.Slider
import com.github.meo209.archer.features.module.settings.compareTo
import com.github.meo209.archer.utils.RotationUtil
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class Killaura: Module("Killaura", Category.COMBAT) {

    @Setting
    var keybind: Keybind = Keybind()
    
    @Setting
    var range = Slider(3.5f, 0f, 12f)
    
    override fun register() {
        EventBus.global().handler(KeyPressEvent::class, { toggle() }, { it.key == keybind.key })
        
        EventBus.global().function<ClientTickEvent>(::onTick) { enabled && inGame }
    }
    
    @FunctionTarget
    private fun onTick(event: ClientTickEvent) {
        val entities = client.world!!.entities

        val target = entities
            .filterIsInstance<LivingEntity>()
            .filter { it != client.player && !(it is PlayerEntity && it.isCreative) && client.player!!.distanceTo(it) >= range }
            .minByOrNull { client.player!!.distanceTo(it) }
        
        if (target != null)
            RotationUtil.faceEntity(target)
    }
    
}