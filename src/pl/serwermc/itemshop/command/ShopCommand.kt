package pl.serwermc.itemshop.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.gui.GuiManager
import pl.serwermc.itemshop.shop.ShopGui
import pl.serwermc.itemshop.shop.ShopGuiFactory
import pl.serwermc.itemshop.token.Tokens
import pl.serwermc.itemshop.util.message

class ShopCommand(
    cooldownSeconds: Long,
    private val guiManager: GuiManager,
    private val shopGuiFactory: ShopGuiFactory
) : CooldownCommand(cooldownSeconds, "itemshop.admin") {

    override fun executeCooldown(sender: CommandSender, args: List<String>) {
        if (sender !is Player) {
            sender.message("playerOnly")
            return
        }

        guiManager.open(sender, shopGuiFactory.create(sender))
    }

}