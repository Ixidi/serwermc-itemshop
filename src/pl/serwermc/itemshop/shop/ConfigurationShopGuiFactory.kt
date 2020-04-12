package pl.serwermc.itemshop.shop

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.data.Configuration
import pl.serwermc.itemshop.gui.Gui
import pl.serwermc.itemshop.shop.offer.Offers
import pl.serwermc.itemshop.token.Tokens

class ConfigurationShopGuiFactory(
    private val configuration: Configuration,
    private val offers: Offers,
    private val tokens: Tokens,
    private val plugin: Plugin
) : ShopGuiFactory {

    override fun create(player: Player): Gui = ShopGui(
        rows = configuration.gui.rows,
        title = configuration.gui.title,
        headItemSlot = configuration.gui.headItem.guiSlot,
        headItemName = configuration.gui.headItem.name,
        headItemLore = configuration.gui.headItem.lore,
        loadingItemMaterial = Material.matchMaterial(configuration.gui.loadingItem.material.toUpperCase().replace(" ", "_")) ?: Material.WHITE_STAINED_GLASS_PANE,
        loadingItemAmount = configuration.gui.loadingItem.amount,
        loadingItemName = configuration.gui.loadingItem.name,
        loadingItemLore = configuration.gui.loadingItem.lore,
        offers = offers,
        tokens = tokens,
        player = player,
        plugin = plugin
    )

}