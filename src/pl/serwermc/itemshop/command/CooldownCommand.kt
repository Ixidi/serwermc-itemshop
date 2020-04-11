package pl.serwermc.itemshop.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.serwermc.itemshop.util.message
import java.util.*
import kotlin.collections.HashMap

abstract class CooldownCommand(
    cooldownSeconds: Long,
    private val bypassPermission: String? = null
) : CommandExecutor {

    private val cooldown = cooldownSeconds * 1000
    private val lastUsage = HashMap<UUID, Long>()

    final override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (bypassPermission != null && !sender.hasPermission(bypassPermission)) {
                val uuid = sender.uniqueId
                val usage = lastUsage[uuid]

                if (usage != null) {
                    if ((System.currentTimeMillis() - usage) < cooldown) {
                        sender.message(
                            "cooldown",
                            "remaining" to (cooldown - (System.currentTimeMillis() - usage)) / 1000
                        )
                        return true
                    }
                }

                lastUsage[uuid] = System.currentTimeMillis()
            }
        }

        executeCooldown(sender, args.toList())
        return true
    }


    protected abstract fun executeCooldown(sender: CommandSender, args: List<String>)

}