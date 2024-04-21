package me.mcyeet.silentium.listeners

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import me.mcyeet.silentium.Silentium.Companion.IPBlacklist
import me.mcyeet.silentium.utils.DebugLogger.debug

class PacketListener: PacketListenerAbstract() {
    override fun onPacketReceive(event: PacketReceiveEvent) {
        if (!IPBlacklist.contains(event.socketAddress.address))
            return

        debug("Rejecting packet from ${event.socketAddress}")
        event.isCancelled = true
    }
}