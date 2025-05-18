package com.github.ringoame196_s_mcPlugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Command : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(
                CommandConst.START_COMMAND,
                CommandConst.STOP_COMMAND,
                CommandConst.TARGET_COMMAND
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
