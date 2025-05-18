package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.GunManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class Command(plugin: Plugin) : CommandExecutor, TabCompleter {
    private val gunManager = GunManager(plugin)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val subCommand = args[0]
        when (subCommand) {
            CommandConst.GIVE_COMMAND -> give(sender as? Player ?: return true)
        }

        return true
    }

    private fun give(player: Player) {
        val gunItem = ItemStack(gunManager.gunItemType)
        val itemMeta = gunItem.itemMeta
        itemMeta?.setDisplayName(gunManager.gunItemName)
        gunItem.itemMeta = itemMeta
        gunManager.reload(gunItem)

        player.inventory.addItem(gunItem)
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(
                CommandConst.START_COMMAND,
                CommandConst.STOP_COMMAND,
                CommandConst.TARGET_COMMAND,
                CommandConst.GIVE_COMMAND
            )
            2 -> when (args[0]) {
                CommandConst.TARGET_COMMAND -> mutableListOf(
                    CommandConst.ADD_SUB_COMMAND,
                    CommandConst.REMOVE_SUB_COMMAND,
                    CommandConst.CHECK_SUB_COMMAND
                )
                else -> mutableListOf()
            }
            else -> mutableListOf()
        }
    }
}
