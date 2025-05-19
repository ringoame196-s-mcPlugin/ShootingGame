package com.github.ringoame196_s_mcPlugin

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class GameManager(plugin: Plugin) {
    private val targetManager = TargetManager(plugin)

    fun start(sender: CommandSender) {
        if (Data.gameStatus) {
            val message = "${ChatColor.RED}既にゲームスタートしています"
            sender.sendMessage(message)
            return
        }
        if (Data.targetList.isEmpty()) {
            val message = "${ChatColor.RED}ターゲットを1つ以上設定してください"
            sender.sendMessage(message)
            return
        }
        val startMessage = "${ChatColor.YELLOW}シューティングゲーム スタート"
        Bukkit.broadcastMessage(startMessage)
        targetManager.randomSummon()
        Data.gameStatus = true
    }

    fun stop() {
        if (!Data.gameStatus) return

        Bukkit.broadcastMessage("${ChatColor.GOLD}シューティングゲーム 終了")
        // 結果表示
        Bukkit.broadcastMessage("${ChatColor.YELLOW}[結果]")
        for ((player, hitCount) in Data.playerHitData) {
            Bukkit.broadcastMessage("${ChatColor.GREEN}${player.displayName} > ${hitCount}HIT")
        }

        // リセット処理
        Data.target?.remove()
        Data.gameStatus = false
        Data.targetHitCount = 0
        Data.playerHitData.clear()
    }
}
