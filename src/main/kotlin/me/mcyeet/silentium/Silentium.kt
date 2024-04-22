package me.mcyeet.silentium

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerAbstract
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import me.mcyeet.silentium.utils.Command
import me.mcyeet.silentium.utils.DebugLogger
import me.mcyeet.silentium.utils.DebugLogger.debug
import me.mcyeet.silentium.utils.YamlDocument
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.reflections.Reflections
import java.io.File
import java.io.FileNotFoundException
import java.net.InetAddress
import java.net.URI
import java.net.URL
import java.util.concurrent.TimeUnit

class Silentium: JavaPlugin() {
    companion object {
        lateinit var Config: YamlDocument
        lateinit var Plugin: JavaPlugin

        private lateinit var IPBlacklistCache: LoadingCache<String, List<InetAddress>>
        val IPBlacklist: List<InetAddress> get() = IPBlacklistCache.get("")
    }

    override fun onLoad() {
        Plugin = this

        val defaultConfig = getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
        Config = YamlDocument.withDefault(File(dataFolder, "config.yml"), defaultConfig)

        //Load PacketEvents
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(Plugin))
        PacketEvents.getAPI().settings.checkForUpdates(false)

        //Load CommandAPI
        CommandAPI.onLoad(CommandAPIBukkitConfig(Plugin).silentLogs(true))

        IPBlacklistCache = Caffeine.newBuilder().refreshAfterWrite(Config.get("Cache_Time"), TimeUnit.MINUTES).build { _ ->
            debug("Updating IP blacklist...")

            val blacklistURL: URL = URI("https://raw.githubusercontent.com/pebblehost/hunter/master/ips.txt").toURL()
            blacklistURL.readText().lines().filter { it.isNotBlank() }
                .map { InetAddress.getByName(it) }
        }
    }

    override fun onEnable() {
        val reflections = Reflections(this.classLoader)

        //val eventClasses = reflections.getSubTypesOf(Listener::class.java)
        //eventClasses.forEach {
        //    val eventClass = it.getDeclaredConstructor().newInstance()
        //    Bukkit.getPluginManager().registerEvents(eventClass, Plugin)
        //}

        reflections.getSubTypesOf(Command::class.java).apply {
            this.forEach {
                val objectInstanceField = it.getDeclaredField("INSTANCE")
                val command = objectInstanceField.get(null) as Command
                command.register()
            }

            logger.info("Successfully registered $size commands!")
        }

        reflections.getSubTypesOf(PacketListenerAbstract::class.java).apply {
            this.forEach {
                val eventClass = it.getDeclaredConstructor().newInstance()
                PacketEvents.getAPI().eventManager.registerListener(eventClass)
            }

            logger.info("Successfully registered $size packet listeners!")
        }

        PacketEvents.getAPI().init()

        //This exists here to make caffeine load the IPBlacklist for the first time, since I think its lazy
        logger.info("Loading IP blacklist...")
        logger.info("  Loaded ${IPBlacklist.size} IP addresses into the blacklist!")
    }

    override fun onDisable() {
        //Terminate PacketEvents
        PacketEvents.getAPI().terminate()

        //Terminate CommandAPI
        CommandAPI.onDisable()

        DebugLogger.fileWriter.close()
    }
}
