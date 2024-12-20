package com.github.meo209.archer

import net.fabricmc.loader.api.FabricLoader
import java.io.File

object FileHandler {

    val ROOT_DIRECTORY = File(FabricLoader.getInstance().configDir.toFile(), "archer")
    val MODULE_DIRECTORY = File(ROOT_DIRECTORY, "modules")

    init {
        if (!ROOT_DIRECTORY.exists()) ROOT_DIRECTORY.mkdir()

        if (!MODULE_DIRECTORY.exists()) MODULE_DIRECTORY.mkdir()
    }

    // Statically load the FileManager
    fun init() {}

}