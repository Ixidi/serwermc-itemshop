package pl.serwermc.itemshop.util

import org.bukkit.command.CommandSender
import org.koin.core.get
import pl.serwermc.itemshop.data.Configuration
import pl.serwermc.itemshop.injection.Injection

fun CommandSender.message(key: String, vararg parameters: Pair<String, Any?>) {
    var message = Injection.get<Configuration>().messages[key] ?: key
    parameters.forEach { message = message.replace("{${it.first}}", "${it.second}") }
    sendMessage(message.color())
}