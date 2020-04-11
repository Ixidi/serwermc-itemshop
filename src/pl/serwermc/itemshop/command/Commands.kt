package pl.serwermc.itemshop.command

import org.bukkit.command.CommandExecutor

class Commands(vararg commands: Pair<String, CommandExecutor>) {

    private val commandMap = mapOf(*commands)

    fun getAll() = commandMap.toMap()

}