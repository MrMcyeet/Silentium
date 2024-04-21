package me.mcyeet.silentium.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.LiteralArgument
import dev.jorel.commandapi.executors.CommandArguments
import me.mcyeet.silentium.Silentium.Companion.Config
import me.mcyeet.silentium.Silentium.Companion.Plugin
import me.mcyeet.silentium.utils.Command
import me.mcyeet.silentium.utils.YamlDocument
import org.bukkit.command.CommandSender
import java.io.File
import java.io.FileNotFoundException

object Reload: Command {
    override fun register() {
        CommandAPICommand("Silentium")
            .withArguments(LiteralArgument("reload"))
            .withPermission("silentium.reload")
            .executes(::run)
            .register()
    }

    private fun run(sender: CommandSender, args: CommandArguments) {
        try {
            val defaultConfig = Plugin.getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
            Config = YamlDocument.withDefault(File(Plugin.dataFolder, "config.yml"), defaultConfig)
            sender.sendMessage("§aSuccessfully reloaded!")
        } catch (e: Exception) {
            sender.sendMessage("§cThere was an error when reloading. Check console logs for more info.")
            e.printStackTrace()
        }
    }
}