package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.ModuleDisableEvent
import com.github.meo209.archer.events.ModuleEnableEvent
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient
import kotlin.properties.ReadWriteProperty

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
abstract class Module(val name: String, val category: Category) {
    
    val properties = mutableSetOf<ModuleProperty<*>>()
    
    @get:JsonProperty
    open var enabled by boolean("Enabled")

    val client
        get() = MinecraftClient.getInstance()
    val player
        get() = client.player
    val world
        get() = client.world
    val network
        get() = client.networkHandler

    val inGame: Boolean
        get() = player != null && world != null
    val inGameScreen: Boolean
        get() = inGame && client.currentScreen == null

    abstract fun init()

    fun toggle() {
        enabled = !enabled
        if (enabled)
            EventBus.global().post(ModuleEnableEvent(this))
        else
            EventBus.global().post(ModuleDisableEvent(this))
    }

    open fun stop() {
        ModuleIO.save(this)
    }

    fun boolean(name: String): ReadWriteProperty<Module, Boolean> =
        ModuleProperty(name, false).also { this.properties.add(it) }

    fun keybind(name: String, default: Int = -1): ReadWriteProperty<Module, Int> =
        ModuleProperty(name, default).also { this.properties.add(it) }
}