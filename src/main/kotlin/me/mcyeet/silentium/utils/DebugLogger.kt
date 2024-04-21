package me.mcyeet.silentium.utils

import me.mcyeet.silentium.Silentium.Companion.Config
import me.mcyeet.silentium.Silentium.Companion.Plugin

object DebugLogger {

    /**
     * Logs a debug message if debugging is enabled in the configuration.
     * Uses the warning level to stand out as highlighted to be easier to see at a glance
     *
     * @param data The data to log. This can be any type of object.
     */
    fun debug(data: Any) {
        if (!Config.get<Boolean>("Debug"))
            return

        Plugin.logger.warning("[DEBUG] $data")
    }

}