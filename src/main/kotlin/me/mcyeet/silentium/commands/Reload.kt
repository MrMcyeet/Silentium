package me.mcyeet.silentium.commands

import me.mcyeet.silentium.utils.Command

object Reload: Command {
    override fun register() {
        //CommandAPICommand("Silentium")
        //    .withArguments(LiteralArgument("reload"))
        //    .withPermission("silentium.reload")
        //    .executes(::run)
        //    .register()
    }

    //private fun run(sender: CommandSender, args: CommandArguments) {
    //    try {
    //        val defaultConfig = Plugin.getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
    //        Config = YamlDocument.withDefault(File(Plugin.dataFolder, "config.yml"), defaultConfig)
    //        sender.sendMessage("§aSuccessfully reloaded!")
    //    } catch (e: Exception) {
    //        sender.sendMessage("§cThere was an error when reloading. Check console logs for more info.")
    //        e.printStackTrace()
    //    }
    //}
}