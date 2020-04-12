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

        if (args.size < 3) {
            sender.message("usage")
            return
        }

        val value = args[2].toIntOrNull()
        if (value == null) {
            sender.message("notValidInteger")
            return
        }

        when (args[1].toLowerCase()) {
            "set" -> {
                plugin.runAsync(
                    action = { tokens.setTokens(target, value) },
                    syncCallback = { sender.message("tokensSet", "player" to target, "tokens" to value) },
                    syncError = {
                        when (it) {
                            is Tokens.NegativeValueException -> sender.message("notValidInteger")
                            else -> { sender.message("unexpectedError"); it.printStackTrace() }
                        }
                    }
                )
            }
            "add" -> {
                plugin.runAsync(
                    action = { tokens.addTokens(target, value) },
                    syncCallback = { sender.message("tokensAdded", "player" to target, "tokens" to value) },
                    syncError = {
                        when (it) {
                            is Tokens.NegativeValueException -> sender.message("notValidInteger")
                            is Tokens.MaxTokensValueException -> sender.message("tokensMax")
                            else -> { sender.message("unexpectedError"); it.printStackTrace() }
                        }
                    }
                )
            }
            "remove" -> {
                plugin.runAsync(
                    action = { tokens.removeTokens(target, value) },
                    syncCallback = { sender.message("tokensRemoved", "player" to target, "tokens" to value) },
                    syncError = {
                        when (it) {
                            is Tokens.NegativeValueException -> sender.message("notValidInteger")
                            is Tokens.NotEnoughTokensException -> sender.message("tokensNotEnough", "player" to target)
                            else -> { sender.message("unexpectedError"); it.printStackTrace() }
                        }
                    }
                )
            }
            else -> sender.message("usage")
        }
    }

}