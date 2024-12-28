package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.meo209.archer.events.ModuleDisableEvent
import com.github.meo209.archer.events.ModuleEnableEvent
import com.github.meo209.archer.features.module.specific.Keybinding
import com.github.meo209.archer.features.module.specific.RangedValue
import com.github.meo209.keventbus.EventBus
import net.minecraft.client.MinecraftClient
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.reflect

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
abstract class Module(val name: String, val category: Category) {
    
    val properties = mutableSetOf<ModuleProperty<*>>()
    
    @get:JsonProperty
    open var enabled by boolean("Enabled", false)

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

    fun boolean(name: String, default: Boolean) =
        ModuleProperty<Boolean>(name, default).also { properties.add(it) }

    fun string(name: String, default: String) =
        ModuleProperty<String>(name, default).also { properties.add(it) }

    fun int(name: String, default: Int) =
        ModuleProperty<Int>(name, default).also { properties.add(it) }

    fun keybinding(name: String, default: Int = -1) =
        ModuleProperty<Keybinding>(name, Keybinding(default)).also { properties.add(it) }
    
    fun range(name: String, value: Float, range: ClosedFloatingPointRange<Float>) =
        ModuleProperty<RangedValue>(name, RangedValue(value, range)).also { properties.add(it) }

}