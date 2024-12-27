package com.github.meo209.archer.utils.math

import ExponentialRR
import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.features.module.Option
import com.github.meo209.archer.utils.math.rr.SimpleRR
import com.github.meo209.archer.utils.math.rr.SineWaveRR

/**
 * An interface that defines a simple function used to transform / randomize rotations or vec2's
 */
@Option(
    SimpleRR::class,
    ExponentialRR::class,
    SineWaveRR::class
)
interface RotationRandomizer {
    
    fun randomize(input: Vec2): Vec2
    
}