package com.github.meo209.archer.features.module

import com.github.meo209.archer.FileHandler
import com.github.meo209.archer.events.ClientShutdownEvent
import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.features.module.serialization.ModuleExclusionStrategy
import com.github.meo209.archer.features.module.serialization.ModuleTypeAdapter
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import com.google.gson.GsonBuilder
import java.io.File

abstract class Module(@Transient val name: String = "", @Transient val category: Category = Category.OTHER) {

    @Transient
    private val gson = GsonBuilder()
        .registerTypeAdapter(Module::class.java, ModuleTypeAdapter())
        .setExclusionStrategies(ModuleExclusionStrategy())
        .setPrettyPrinting()
        .create()

    @Transient
    private val configFile = File(FileHandler.MODULE_DIRECTORY, "$name.json")

    @Include
    public val type = javaClass.name

    @Include
    var enabled: Boolean = false

    init {
        EventBus.global().function<ClientStartEvent>(::loadConfiguration)

        EventBus.global().function<ClientShutdownEvent>(::saveConfiguration)
    }

    open fun initAfterConfig() {}

    @FunctionTarget
    private fun loadConfiguration(event: ClientStartEvent) {
        if (!configFile.exists() || configFile.readText().isEmpty()) return

        val loaded = gson.fromJson(configFile.readText(), Module::class.java)

        this::class.java.declaredFields.forEach { field ->
            if (field.name == "INSTANCE") return@forEach // Skip companion object INSTANCE field

            val loadedField = loaded::class.java.declaredFields.first { it.name == field.name }

            field.isAccessible = true
            loadedField.isAccessible = true
            field.set(this, loadedField.get(loaded))
        }

        initAfterConfig()
    }

    @FunctionTarget
    private fun saveConfiguration(event: ClientShutdownEvent) {
        println("Saving config $name")
        if (!configFile.exists()) configFile.createNewFile()

        configFile.writeText(gson.toJson(this))
    }

    enum class Category {
        OTHER
    }

}