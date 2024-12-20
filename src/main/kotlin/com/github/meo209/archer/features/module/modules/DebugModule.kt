package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.features.module.Include
import com.github.meo209.archer.features.module.Module

class DebugModule : Module(name = "Debug", Category.OTHER) {

    @Include
    var exampleSetting = "Hello World"

    @Include
    val slider = 1..10

    override fun initAfterConfig() {
        println(exampleSetting)
    }

}