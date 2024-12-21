package com.github.meo209.archer.utils.math

import com.github.meo209.archer.features.module.Include
import net.minecraft.util.math.Vec2f

data class Vec2(@Include var x: Double, @Include var y: Double) {

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
