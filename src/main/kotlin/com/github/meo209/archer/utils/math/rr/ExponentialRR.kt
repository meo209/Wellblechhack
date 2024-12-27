package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import java.util.random.RandomGenerator

class ExponentialRR(
    var yawLambda: Double = 1.0,
    var pitchLambda: Double = 1.0
) : RotationRandomizer {

    private val randomGenerator: RandomGenerator = RandomGenerator.getDefault()

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + randomGenerator.nextExponential() / yawLambda,
            input.y + randomGenerator.nextExponential() / pitchLambda
        )
    }
}