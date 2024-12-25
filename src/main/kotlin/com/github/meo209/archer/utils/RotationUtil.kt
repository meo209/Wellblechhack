package com.github.meo209.archer.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.util.math.Vec3d
import kotlin.math.atan2
import kotlin.math.sqrt

object RotationUtil {

    private val client: MinecraftClient = MinecraftClient.getInstance()

    fun face(vec3d: Vec3d) {
        val playerPos: Vec3d = client.player!!.eyePos

        val targetPos: Vec3d = vec3d

        val diffX: Double = targetPos.x - playerPos.x
        val diffY: Double = targetPos.y - playerPos.y
        val diffZ: Double = targetPos.z - playerPos.z

        val dist: Double = sqrt((diffX * diffX + diffZ * diffZ))
        val yaw: Double = atan2(diffZ, diffX) * (180.0 / Math.PI) - 90.0
        val pitch: Double = atan2(diffY, dist) * (180.0 / Math.PI)

        client.player!!.yaw = yaw.toFloat()
        client.player!!.pitch = pitch.toFloat()
    }

    fun faceEntity(target: Entity) {
        face(target.boundingBox.center)
    }
}