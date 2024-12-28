/*
 * This file is part of Archer (https://github.com/meo209/Archer)
 * Copyright (c) 2024 - meo209
 *
 * Archer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Archer program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 */

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