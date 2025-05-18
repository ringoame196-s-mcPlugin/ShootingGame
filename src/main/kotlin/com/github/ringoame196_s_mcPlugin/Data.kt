package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.entity.Pig

object Data {
    var gameStatus: Boolean = false
    val targetList = mutableSetOf<Location>()
    var firingRangeDistance: Double = 30.0
    var maxBullet: Int = 15
    var target: Pig? = null

    const val TARGET_LIST_FILE_NAME = "target_list.yml"
}
