package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.events.GunEvent
import com.github.ringoame196_s_mcPlugin.managers.TargetManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        // config関係
        saveDefaultConfig()
        loadConfig()

        // targetList関係
        saveResource(Data.TARGET_LIST_FILE_NAME, false)
        val targetManager = TargetManager(plugin)
        targetManager.loadFile()

        server.pluginManager.registerEvents(GunEvent(plugin), plugin)
        val command = getCommand("shootinggame")
        command!!.setExecutor(Command(plugin))
    }

    private fun loadConfig() {
        Data.firingRangeDistance = config.getDouble("firing_range_distance")
        Data.maxBullet = config.getInt("max_bullet")
        Data.targetGoal = config.getInt("target_goal")
    }
}
