package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.meo209.archer.events.ModuleDisableEvent
import com.github.meo209.archer.events.ModuleEnableEvent
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient

abstract class Module(@JsonIgnore val name: String, @JsonIgnore val category: Category) {

    open var enabled = false

    @get:JsonIgnore
    val client
        get() = MinecraftClient.getInstance()
    @get:JsonIgnore
    val player
        get() = client.player
    @get:JsonIgnore
    val world
        get() = client.world
    @get:JsonIgnore
    val network
        get() = client.networkHandler

    @get:JsonIgnore
    val inGame: Boolean
        get() = player != null && world != null
    @get:JsonIgnore
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

    fun load() {
        ModuleIO.load(this)
    }

    fun save() {
        ModuleIO.save(this)
    }
}