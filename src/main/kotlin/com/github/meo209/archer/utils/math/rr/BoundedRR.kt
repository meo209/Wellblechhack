package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import kotlin.random.Random

class BoundedRR(
    var minYaw: Float = -10f,
    var maxYaw: Float = 10f,
    var minPitch: Float = -15f,
    var maxPitch: Float = 15f
) : RotationRandomizer {

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            (input.x + Random.nextDouble(minYaw.toDouble(), maxYaw.toDouble())).coerceIn(minYaw.toDouble(), maxYaw.toDouble()),
            (input.y + Random.nextDouble(minPitch.toDouble(), maxPitch.toDouble())).coerceIn(minPitch.toDouble(), maxPitch.toDouble())
        )
    }
}