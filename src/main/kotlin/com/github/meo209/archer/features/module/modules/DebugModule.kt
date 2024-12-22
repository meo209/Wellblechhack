package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.settings.Slider
import kotlinx.serialization.Serializable

class DebugModule : Module("Debug", Module.Category.OTHER) {

    var test = "Hello"
    var slider = Slider(.5f, 0f, 1f)

    override fun register() {
        println(test)
    }

}