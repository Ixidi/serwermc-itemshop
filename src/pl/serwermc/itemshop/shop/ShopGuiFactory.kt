package pl.serwermc.itemshop.shop

import org.bukkit.entity.Player
import pl.serwermc.itemshop.gui.Gui

interface ShopGuiFactory {

    fun create(player: Player): Gui

}