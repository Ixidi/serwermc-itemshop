package pl.serwermc.itemshop.gui

import org.bukkit.event.inventory.InventoryClickEvent

interface GuiAction {

    companion object {

        val NOTHING = object : GuiAction {
            override fun handle(event: InventoryClickEvent) {}
        }

    }

    fun handle(event: InventoryClickEvent)

}