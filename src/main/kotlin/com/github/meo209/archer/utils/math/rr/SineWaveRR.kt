package com.github.meo209.archer.utils.math.rr

import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import kotlin.math.sin

class SineWaveRR(
    @property:ClickGui
    var amplitudeYaw: Float = 4f,
    @property:ClickGui
    var amplitudePitch: Float = 5f,
    @property:ClickGui
    var frequency: Float = 1f
) : RotationRandomizer {

    private var time: Double = 0.0

    override fun randomize(input: Vec2): Vec2 {
        time += 0.1
        return Vec2(
            input.x + amplitudeYaw.toDouble() * sin(frequency.toDouble() * time),
            input.y + amplitudePitch.toDouble() * sin(frequency.toDouble() * time)
        )
    }
}