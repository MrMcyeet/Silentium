package me.mcyeet.silentium.utils

import me.mcyeet.silentium.Silentium.Companion.Config
import me.mcyeet.silentium.Silentium.Companion.Plugin
import org.bukkit.Bukkit
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DebugLogger {
    val fileWriter by lazy {
        File(Plugin.dataFolder, "debug.log").apply {
            createNewFile()
        }.bufferedWriter()
    }

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
        Bukkit.getScheduler().runTaskAsynchronously(Plugin, Runnable {
            val formatter = DateTimeFormatter.ofPattern("h:mma MM/dd")
            val dateTime = LocalDateTime.now().format(formatter).replace("AM", "am").replace("PM", "pm")

            fileWriter.appendLine("[$dateTime] $data")
            fileWriter.flush()
        })
    }

}