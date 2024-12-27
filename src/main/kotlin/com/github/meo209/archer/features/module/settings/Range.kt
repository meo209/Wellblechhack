package com.github.meo209.archer.features.module.settings

data class Range(
    var value: Float,
    val min: Float,
    val max: Float
) {
    
    init {
        require(value in min..max) { "Value needs to be in range of min..max" }
    }
    
}