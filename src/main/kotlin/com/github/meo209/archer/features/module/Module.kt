package com.github.meo209.archer.features.module

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.meo209.archer.FileHandler
import com.github.meo209.archer.events.*
import com.github.meo209.keventbus.EventBus
import com.github.meo209.keventbus.FunctionTarget
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager
import java.io.File

abstract class Module(
    @JsonIgnore val name: String, @JsonIgnore val category: Category
) {

    private val logger = LogManager.getLogger("${name}Module")

    private val configFile = File(FileHandler.MODULE_DIRECTORY, "$name.json")

    private val mapper = ObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Setting
    open var enabled: Boolean = false

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
    
    fun toggle() {
        enabled = !enabled
    }

    init {
        logger.debug("Registering event handlers")

        EventBus.global().handler(ClientStartEvent::class, { _: ClientStartEvent ->
            loadConfiguration()
            register()
        })

        EventBus.global().function<ClientShutdownEvent>(::saveConfiguration)
    }

    open fun register() {}

    private fun loadConfiguration() {
        if (!configFile.exists() || configFile.readText().isEmpty()) return

        logger.debug("Loading config from file")

        mapper.readerForUpdating(this).readValue<Module>(configFile.readText())
    }

    @FunctionTarget
    private fun saveConfiguration(event: ClientShutdownEvent) {
        if (!configFile.exists()) configFile.createNewFile()

        logger.debug("Writing config to file")

        configFile.writeText(mapper.writeValueAsString(this))
    }

    enum class Category {
        OTHER, RENDER, MOVEMENT, PLAYER, WORLD, COMBAT
    }

}