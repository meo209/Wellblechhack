package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import java.util.Random

class GaussianRR(
    var maxYaw: Float = 4f,
    var maxPitch: Float = 5f
) : RotationRandomizer {

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + Random().nextGaussian() * maxYaw.toDouble(),
            input.y + Random().nextGaussian() * maxPitch.toDouble()
        )
    }
}