package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import kotlin.random.Random

class MomentumRR(
    var maxYaw: Float = 4f,
    var maxPitch: Float = 5f,
    private var momentumFactor: Float = 0.5f
) : RotationRandomizer {

    private var previousDelta: Vec2 = Vec2(0.0, 0.0)

    override fun randomize(input: Vec2): Vec2 {
        val deltaYaw = Random.nextDouble(-maxYaw.toDouble(), maxYaw.toDouble())
        val deltaPitch = Random.nextDouble(-maxPitch.toDouble(), maxPitch.toDouble())

        val newDelta = Vec2(
            deltaYaw + previousDelta.x * momentumFactor,
            deltaPitch + previousDelta.y * momentumFactor
        )

        previousDelta = newDelta

        return Vec2(
            input.x + newDelta.x,
            input.y + newDelta.y
        )
    }
}