package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.managers.GunManager
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class GUN(plugin: Plugin) {
    private val gunManager = GunManager(plugin)
    private val maxBullet = Data.maxBullet

    fun shot(player: Player, gun: ItemStack): LivingEntity? {
        val firingRangeDistance = Data.firingRangeDistance
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

        val bullet = gunManager.acquisitionBullet(gun)
        gunManager.setBullet(gun, bullet - 1)
        gunManager.displayBullet(gun, maxBullet)

        val result = player.world.rayTraceEntities(
            eyeLocation,
            direction,
            maxDistance
        ) { entity -> entity != player && entity is LivingEntity } ?: return null
        return result.hitEntity as LivingEntity
    }

    fun reload(gun: ItemStack) {
        gunManager.setBullet(gun, maxBullet)
        gunManager.displayBullet(gun, maxBullet)
    }

    fun makeItem(): ItemStack {
        val gunItem = ItemStack(gunManager.gunItemType)
        val itemMeta = gunItem.itemMeta
        itemMeta?.setDisplayName(gunManager.gunItemName)
        gunItem.itemMeta = itemMeta
        reload(gunItem)
        return gunItem
    }
}
