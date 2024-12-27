package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import kotlin.random.Random

class StepRR(
    @property:ClickGui
    var yawStep: Float = 2f,
    @property:ClickGui
    var pitchStep: Float = 2.5f
) : RotationRandomizer {

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + Random.nextInt(-1, 2) * yawStep.toDouble(),
            input.y + Random.nextInt(-1, 2) * pitchStep.toDouble()
        )
    }
}