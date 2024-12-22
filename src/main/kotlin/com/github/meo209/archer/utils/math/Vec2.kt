package com.github.meo209.archer.utils.math

import kotlinx.serialization.Serializable
import net.minecraft.util.math.Vec2f

@Serializable
data class Vec2(var x: Double, var y: Double) {

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
