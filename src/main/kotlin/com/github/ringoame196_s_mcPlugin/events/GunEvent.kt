package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GunManager
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin

class GunEvent(plugin: Plugin) : Listener {
    private val gunManager = GunManager(plugin)

    @EventHandler
    fun shot(e: PlayerInteractEvent) {
        val player = e.player
        val item = e.item ?: return
        val action = e.action

        if (!gunManager.checkGun(item)) return
        e.isCancelled = true
        if (player.hasCooldown(item.type)) return

        when (action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> {
                if (gunManager.acquisitionBullet(item) <= 0) {
                    val sound = Sound.BLOCK_DISPENSER_DISPENSE
                    player.playSound(player, sound, 1f, 1f)
                    return
                }
                val hit = gunManager.shot(player, item)
            }
            else -> {
                gunManager.reload(item)
                val sound = Sound.BLOCK_IRON_DOOR_OPEN
                player.playSound(player, sound, 1f, 1f)
                player.setCooldown(item.type, 20)
            }
        }
    }
}
