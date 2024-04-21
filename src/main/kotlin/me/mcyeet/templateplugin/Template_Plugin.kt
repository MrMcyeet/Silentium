package me.mcyeet.templateplugin

import com.github.retrooper.packetevents.PacketEvents
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import me.mcyeet.templateplugin.utils.Command
import me.mcyeet.templateplugin.utils.YamlDocument
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import java.io.File
import java.io.FileNotFoundException

class Template_Plugin: JavaPlugin() {
    companion object {
        lateinit var Config: YamlDocument
        lateinit var Plugin: JavaPlugin
    }

    override fun onLoad() {
        Plugin = this

        val defaultConfig = getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
        Config = YamlDocument.withDefault(File(dataFolder, "config.yml"), defaultConfig)

        //Load PacketEvents
        //PacketEvents.setAPI(SpigotPacketEventsBuilder.build(Plugin))
        //PacketEvents.getAPI().settings.checkForUpdates(false)

        //Load CommandAPI
        //CommandAPI.onLoad(CommandAPIBukkitConfig(Plugin).silentLogs(true))
    }

    override fun onEnable() {
        val reflections = Reflections(this.classLoader)

        val eventClasses = reflections.getSubTypesOf(Listener::class.java)
        eventClasses.forEach {
            val eventClass = it.getDeclaredConstructor().newInstance()
            Bukkit.getPluginManager().registerEvents(eventClass, Plugin)
        }

        //val commandClasses = reflections.getSubTypesOf(Command::class.java)
        //commandClasses.forEach {
        //    val objectInstanceField = it.getDeclaredField("INSTANCE")
        //    val command = objectInstanceField.get(null) as Command
        //    command.register()
        //}

        //PacketEvents.getAPI().init()
    }

    override fun onDisable() {
        //Terminate PacketEvents
        //PacketEvents.getAPI().terminate()

        //Terminate CommandAPI
        //CommandAPI.onDisable()
    }
}
