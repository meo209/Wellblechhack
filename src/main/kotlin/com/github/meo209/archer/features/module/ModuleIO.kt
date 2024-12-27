package com.github.meo209.archer.features.module

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.meo209.archer.FileHandler
import java.io.File
import kotlin.reflect.full.memberProperties

object ModuleIO {

    private val objectMapper = ObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerKotlinModule()
    }

    private fun getFileFor(module: Module): File =
        File(FileHandler.MODULE_DIRECTORY, "${module.name}.json").apply {
            if (!exists()) createNewFile()
        }

    fun load(module: Module) {
        val file = getFileFor(module)
        if (file.readText().isEmpty()) return

        objectMapper.readerForUpdating(module).readValue<Module>(file.readText())
    }

    fun save(module: Module) {
        val file = getFileFor(module)

        val json = objectMapper.writeValueAsString(module)
        file.writeText(json)
    }
}