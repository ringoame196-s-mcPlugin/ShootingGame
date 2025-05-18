package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GunManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class GunEvent : Listener {
    private val gunManager = GunManager()

    @EventHandler
    fun shot(e: PlayerInteractEvent) {
        val player = e.player
        val item = e.item ?: return
        val action = e.action

        if (!gunManager.checkGun(item)) return

        when(action) {
            Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK -> {
                val hit = gunManager.shot(player)
            }
            else -> {

            }
        }
    }
}
