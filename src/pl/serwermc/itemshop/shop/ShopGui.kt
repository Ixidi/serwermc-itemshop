package pl.serwermc.itemshop.shop

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.gui.Gui
import pl.serwermc.itemshop.gui.GuiAction
import pl.serwermc.itemshop.shop.offer.Offers
import pl.serwermc.itemshop.token.Tokens
import pl.serwermc.itemshop.util.itemStack
import pl.serwermc.itemshop.util.message
import pl.serwermc.itemshop.util.runAsync
import pl.serwermc.itemshop.util.skullItemStack

class ShopGui(
    rows: Int,
    title: String,
    private val headItemSlot: Int,
    private val headItemName: String,
    private val headItemLore: String,
    loadingItemMaterial: Material,
    loadingItemAmount: Int,
    loadingItemName: String,
    loadingItemLore: String,
    private val offers: Offers,
    private val tokens: Tokens,
    private val player: Player,
    private val plugin: Plugin
) : Gui(rows, title) {

    private val loadingItem = itemStack(
        material = loadingItemMaterial,
        amount = loadingItemAmount,
        name = loadingItemName,
        lore = loadingItemLore.split("\n")
    )

    override fun initialize() {
        fill(loadingItem, GuiAction.NOTHING)

        plugin.runAsync(
            action = { tokens.getTokens(player.name, true) },
            syncCallback = { tokens ->
                clear()
                setSlot(
                    slot = headItemSlot,
                    itemStack = skullItemStack(
                            owner = player.name,
                            name = headItemName.replace("{tokens}", "$tokens"),
                            lore = headItemLore.replace("{tokens}", "$tokens").split("\n")
                        ),
                        action = GuiAction.NOTHING
                    )

                offers.getAll().forEach {
                    setSlot(
                        slot = it.guiSlot,
                        itemStack = it.itemStack,
                        action = BuyAction(it, this@ShopGui.tokens, plugin)
                    )
                }

            },
            syncError = {
                player.message("unexpectedError")
                it.printStackTrace()
                player.closeInventory()
            }
        )
    }

}