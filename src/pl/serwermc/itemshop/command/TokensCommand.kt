package pl.serwermc.itemshop.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.token.Tokens
import pl.serwermc.itemshop.util.message
import pl.serwermc.itemshop.util.runAsync

class TokensCommand(
    cooldownSeconds: Long,
    private val plugin: Plugin,
    private val tokens: Tokens
) : CooldownCommand(cooldownSeconds, "itemshop.admin") {

    override fun executeCooldown(sender: CommandSender, args: List<String>) {
        if (args.isEmpty() || !sender.hasPermission("itemshop.admin")) {
            if (sender !is Player) {
                sender.message("checkPlayerOnly")
                return
            }

            plugin.runAsync(
                action = { tokens.getTokens(sender.name) },
                syncCallback = { sender.message("tokensCheck", "tokens" to it) },
                syncError = { sender.message("unexpectedError"); it.printStackTrace() }
            )

            return
        }

        val target = args[0]
        if (args.size == 1) {
            plugin.runAsync(
                action = { tokens.getTokens(target, false) },
                syncCallback = { sender.message("tokensCheckPlayer", "player" to target, "tokens" to it) },
                syncError = {
                    when (it) {
                        is Tokens.AccountMissingException -> {
                            if (plugin.server.offlinePlayers.firstOrNull { p -> p.name == target } == null) {
                                sender.message("playerNotExist", "name" to target)
                                return@runAsync
                            }

                            plugin.runAsync(
                                action = { tokens.setTokens(target, 0) },
                                syncCallback = { sender.message("tokensCheckPlayer", "player" to target, "tokens" to 0) },
                                syncError = { t -> sender.message("unexpectedError"); t.printStackTrace() }
                            )
                        }
                        else -> { sender.message("unexpectedError"); it.printStackTrace() }
                    }
                }
            )
            return
        }


    }

}