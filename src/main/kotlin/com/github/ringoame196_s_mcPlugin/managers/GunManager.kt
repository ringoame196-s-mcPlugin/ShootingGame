package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class GunManager(plugin: Plugin) {
    val gunItemType = Material.GOLDEN_HOE
    val gunItemName = "${ChatColor.YELLOW}銃"
    private val bulletKey = NamespacedKey(plugin, "bullet")

    fun checkGun(item: ItemStack): Boolean {
        return item.type == gunItemType && item.itemMeta?.displayName == gunItemName
    }

    fun displayBullet(gun: ItemStack, maxBullet: Int) {
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
