package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import kotlin.random.Random

class SimpleRR(
    var maxYaw: Float = 4f,
    var maxPitch: Float = 5f
) : RotationRandomizer {

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + Random.nextDouble(0.0, maxYaw.toDouble()),
            input.y + Random.nextDouble(0.0, maxPitch.toDouble())
        )
    }

}