package com.github.ringoame196_s_mcPlugin

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class GunManager(plugin: Plugin) {
    val gunItemType = Material.GOLDEN_HOE
    val gunItemName = "${ChatColor.YELLOW}銃"
    private val bulletKey = NamespacedKey(plugin, "bullet")

    private val maxBullet = 15

    fun checkGun(item: ItemStack): Boolean {
        return item.type == gunItemType && item.itemMeta?.displayName == gunItemName
    }

    fun shot(player: Player, gun: ItemStack): LivingEntity? {
        val firingRangeDistance = Data.firingRangeDistance ?: 50.0
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

        val bullet = acquisitionBullet(gun)
        setBullet(gun, bullet - 1)
        displayBullet(gun)

        val result = player.world.rayTraceEntities(
            eyeLocation,
            direction,
            maxDistance
        ) { entity -> entity != player && entity is LivingEntity } ?: return null
        return result.hitEntity as LivingEntity
    }

    fun reload(gun: ItemStack) {
        setBullet(gun, maxBullet)
        displayBullet(gun)
    }

    private fun displayBullet(gun: ItemStack) {
        val itemMeta = gun.itemMeta as Damageable
        val bullet = acquisitionBullet(gun)

        itemMeta.lore = mutableListOf("弾数:$bullet/$maxBullet")

        val usedBullets = maxBullet - bullet
        val damage = (gunItemType.maxDurability * usedBullets) / maxBullet
        itemMeta.damage = damage

        gun.itemMeta = itemMeta
    }

    fun setBullet(gun: ItemStack, count: Int) {
        val itemMeta = gun.itemMeta
        itemMeta?.persistentDataContainer?.set(bulletKey, PersistentDataType.INTEGER, count)
        gun.itemMeta = itemMeta
    }

    fun acquisitionBullet(gun: ItemStack): Int {
        val itemMeta = gun.itemMeta
        return itemMeta?.persistentDataContainer?.get(bulletKey, PersistentDataType.INTEGER) ?: 0
    }
}
