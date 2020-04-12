package pl.serwermc.itemshop.shop

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.gui.GuiAction
import pl.serwermc.itemshop.shop.offer.Offer
import pl.serwermc.itemshop.token.Tokens
import pl.serwermc.itemshop.util.message
import pl.serwermc.itemshop.util.runAsync

class BuyAction(
    private val offer: Offer,
    private val tokens: Tokens,
    private val plugin: Plugin
) : GuiAction {

    override fun handle(event: InventoryClickEvent) {
        plugin.runAsync(
            action = { tokens.removeTokens(event.whoClicked.name, offer.cost) },
            syncCallback = {
                offer.commands(event.whoClicked.name).forEach { plugin.server.dispatchCommand(plugin.server.consoleSender, it) }
                plugin.server.onlinePlayers.forEach {
                    it.message("shopBroadcast", "player" to event.whoClicked.name, "offer" to offer.name, "tokens" to offer.cost)
                }
                event.whoClicked.message("buySuccess")
            },
            syncError = {
                when (it) {
                    is Tokens.NotEnoughTokensException -> { event.whoClicked.message("buyNotEnough") }
                    else -> { event.whoClicked.message("unexpectedError"); it.printStackTrace() }
                }
            }
        )

        event.whoClicked.closeInventory()
    }

}