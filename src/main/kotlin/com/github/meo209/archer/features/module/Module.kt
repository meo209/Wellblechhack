package com.github.meo209.archer.features.module

import com.github.meo209.archer.FileHandler
import com.github.meo209.archer.events.ClientShutdownEvent
import com.github.meo209.archer.events.ClientStartEvent
import com.github.meo209.archer.features.module.serialization.ModuleExclusionStrategy
import com.github.meo209.archer.features.module.serialization.ModuleTypeAdapter
import com.github.meo209.keventbus.Event
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import com.google.gson.GsonBuilder
import org.apache.logging.log4j.LogManager
import java.io.File

abstract class Module(@Transient val name: String = "", @Transient val category: Category = Category.OTHER) {

    private val logger = LogManager.getLogger("${name}Module")

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

    val moduleBus = EventBus.createScoped()

    class InitEventPre: Event
    class InitEventPost: Event

    init {
        logger.debug("Registering event handlers")

        EventBus.global().handler(ClientStartEvent::class) { _: ClientStartEvent ->
            register()
            moduleBus.post(InitEventPre())
            loadConfiguration()
            moduleBus.post(InitEventPost())
        }

        EventBus.global().function<ClientShutdownEvent>(::saveConfiguration)
    }

    open fun register() {}

    private fun loadConfiguration() {
        if (!configFile.exists() || configFile.readText().isEmpty()) return

        logger.debug("Loading config from file")

        val loaded = gson.fromJson(configFile.readText(), Module::class.java)

        this::class.java.declaredFields.forEach { field ->
            if (field.name == "INSTANCE") return@forEach // Skip companion object INSTANCE field

            val loadedField = loaded::class.java.declaredFields.first { it.name == field.name }

            field.isAccessible = true
            loadedField.isAccessible = true

            logger.debug("Changing field ${field.name} to config value")

            field.set(this, loadedField.get(loaded))
        }
    }

    @FunctionTarget
    private fun saveConfiguration(event: ClientShutdownEvent) {
        if (!configFile.exists()) configFile.createNewFile()

        logger.debug("Writing config to file")
        configFile.writeText(gson.toJson(this))
    }

    enum class Category {
        OTHER
    }

}