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

package com.github.meo209.wellblechhack.utils.math

import net.minecraft.util.math.Vec2f

data class Vec2(var x: Double, var y: Double) {
    
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())
    constructor(x: Long, y: Long) : this(x.toDouble(), y.toDouble())
    constructor(x: Byte, y: Byte) : this(x.toDouble(), y.toDouble())
    constructor(x: Short, y: Short) : this(x.toDouble(), y.toDouble())

    operator fun plus(other: Vec2): Vec2 =
        Vec2(this.x + other.x, this.y + other.y)

    operator fun plusAssign(other: Vec2) {
        x += other.x
        y += other.y
    }

    operator fun minus(other: Vec2): Vec2 =
        Vec2(this.x - other.x, this.y - other.y)

    operator fun minusAssign(other: Vec2) {
        x -= other.x
        y -= other.y
    }

    operator fun times(other: Vec2): Vec2 =
        Vec2(this.x * other.x, this.y * other.y)

    operator fun timesAssign(other: Vec2) {
        x *= other.x
        y *= other.y
    }

    operator fun div(other: Vec2): Vec2 =
        Vec2(this.x / other.x, this.y / other.y)

    operator fun divAssign(other: Vec2) {
        x /= other.x
        y /= other.y
    }

    fun toMinecraftVariant(): Vec2f =
        Vec2f(x.toFloat(), y.toFloat())

}
