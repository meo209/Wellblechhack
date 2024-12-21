package com.github.meo209.archer.features.module.modules

import com.github.meo209.archer.features.module.Include
import com.github.meo209.archer.features.module.Module
import com.github.meo209.archer.features.module.setting.Slider

class DebugModule : Module(name = "Debug", Category.OTHER) {

    @Include
    var exampleSetting = "Hello World"

    @Include
    val slider = Slider(.5f, 0f..1f)

    override fun initAfterConfig() {
        println(exampleSetting)
        println(slider.value)
    }

}