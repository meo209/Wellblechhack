package com.github.meo209.archer.features.module.specific

class RangedValue(var value: Float, var range: ClosedFloatingPointRange<Float>) {
    
    init {
        require(value in range) { "Value is not in range for RangedValue." }
    }
    
}