package com.github.meo209.archer.utils.math

/**
 * An interface that defines a simple function used to transform / randomize rotations or vec2's
 */
interface RotationRandomizer {
    
    fun randomize(input: Vec2): Vec2
    
}