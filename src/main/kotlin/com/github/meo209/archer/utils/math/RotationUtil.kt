package com.github.meo209.archer.utils.math

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.util.math.Vec3d
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

// NEVER TOUCH THIS AGAIN
// IT WORKS
// I HATE MATH
object RotationUtil {

    private val client: MinecraftClient = MinecraftClient.getInstance()

    fun face(target: Vec3d, smoothingFactor: Float = .2f): Vec2 {
        val player = client.player!!

        val direction = calculateDirectionVector(
            Vec3d(
                player.pos.x, player.eyeY, player.pos.z
            ), target
        )

        // Horizontal angle
        val yaw = atan2(direction.z, direction.x) * (180 / PI) - 90 // -90 Because Minecraft's coordinate system is ass
        // Vertical angle
        val pitch = -atan2(direction.y, sqrt(direction.x * direction.x + direction.z * direction.z)) * (180 / PI)

        // Interpolate towards the target yaw and pitch, ensuring the shortest path
        val smoothedYaw = interpolateAngle(player.yaw.toDouble(), yaw, smoothingFactor)
        val smoothedPitch = interpolate(player.pitch.toDouble(), pitch, smoothingFactor)

        return Vec2(smoothedYaw, smoothedPitch)
    }

    private fun interpolate(current: Double, target: Double, smoothingFactor: Float): Double {
        return current + (target - current) * smoothingFactor
    }

    private fun interpolateAngle(current: Double, target: Double, smoothingFactor: Float): Double {
        val difference = calculateAngleDifference(current, target)
        val interpolated = current + difference * smoothingFactor
        return normalizeAngle(interpolated)
    }

    private fun calculateAngleDifference(current: Double, target: Double): Double {
        var difference = target - current
        if (difference > 180) {
            difference -= 360
        } else if (difference < -180) {
            difference += 360
        }
        return difference
    }

    private fun normalizeAngle(angle: Double): Double {
        return (angle + 180) % 360 - 180
    }

    private fun calculateDirectionVector(camera: Vec3d, target: Vec3d): Vec3d {
        return Vec3d(target.x - camera.x, target.y - camera.y, target.z - camera.z)
    }

    fun Vec2.applyTo(player: ClientPlayerEntity) {
        player.yaw = x.toFloat()
        player.pitch = y.toFloat()
    }
}