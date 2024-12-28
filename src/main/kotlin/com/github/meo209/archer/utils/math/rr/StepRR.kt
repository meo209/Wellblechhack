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

class StepRR(
    var yawStep: Float = 2f,
    var pitchStep: Float = 2.5f
) : RotationRandomizer {

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + Random.nextInt(-1, 2) * yawStep.toDouble(),
            input.y + Random.nextInt(-1, 2) * pitchStep.toDouble()
        )
    }
}