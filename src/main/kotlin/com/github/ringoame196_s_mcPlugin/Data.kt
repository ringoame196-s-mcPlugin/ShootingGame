package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location

object Data {
    var gameStatus: Boolean = false
    val targetList = mutableSetOf<Location>()
    var firingRangeDistance: Double? = null

    const val TARGET_LIST_FILE_NAME = "target_list.yml"
}
