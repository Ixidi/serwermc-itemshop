package pl.serwermc.itemshop.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiListener(
    private val guiManager: GuiManager
) : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null) return
        if (event.clickedInventory == event.whoClicked.inventory) return

        guiManager.get(event.whoClicked)?.handleClick(event)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        guiManager.remove(event.player)
    }

}