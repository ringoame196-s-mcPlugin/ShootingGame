package com.github.ringoame196_s_mcPlugin

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GunManager {
    private val gunItemType = Material.GOLDEN_HOE
    private val gunItemName = "${ChatColor.YELLOW}銃"

    private val firingRangeDistance = 50.0

    fun checkGun(item: ItemStack): Boolean {
        return item.type == gunItemType && item.itemMeta?.displayName == gunItemName
    }

    fun shot(player: Player): LivingEntity? {
        val sound = Sound.ENTITY_FIREWORK_ROCKET_BLAST
        player.world.playSound(player.location, sound, 1f, 1f)

        val eyeLocation = player.eyeLocation
        val direction = eyeLocation.direction

        val blockHit = player.world.rayTraceBlocks(eyeLocation, direction, firingRangeDistance)
        val maxDistance = blockHit?.hitPosition?.distance(eyeLocation.toVector()) ?: firingRangeDistance

        val step = 0.5 // パーティクルの間隔
        val steps = (maxDistance / step).toInt()

        for (i in 1..steps) {
            val point = eyeLocation.clone().add(direction.clone().multiply(i * step))
            player.world.spawnParticle(
                Particle.CRIT, // 他に FLAME, REDSTONE などもおすすめ
                point,
                1,
                0.0, 0.0, 0.0, 0.0
            )
        }

        val result = player.world.rayTraceEntities(
            eyeLocation,
            direction,
            maxDistance
        ) { entity -> entity != player && entity is LivingEntity } ?: return null
        return result.hitEntity as LivingEntity
    }
}
