/*
 * Wellblechhack
 * Copyright (C) 2024 meo209
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.meo209.wellblechhack.utils.math.rr

import com.github.meo209.wellblechhack.utils.math.RotationRandomizer
import com.github.meo209.wellblechhack.utils.math.Vec2
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