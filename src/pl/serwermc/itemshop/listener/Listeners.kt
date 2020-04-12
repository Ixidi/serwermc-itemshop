package pl.serwermc.itemshop.listener

import org.bukkit.event.Listener


class Listeners(vararg listeners: Listener) {

    private val listenerList = listeners.toList()

    fun getAll() = listenerList.toList()

}