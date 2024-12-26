package com.github.meo209.archer.features.module.settings

data class Slider(var value: Float = 0f, val min: Float = 0f, val max: Float = 1f) {
    
    operator fun compareTo(float: Float): Int {
        return value.compareTo(float)
    }
    
}

operator fun Float.compareTo(slider: Slider): Int {
    return this.compareTo(slider.value)
}