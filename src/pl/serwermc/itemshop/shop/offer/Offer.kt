package pl.serwermc.itemshop.shop.offer

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class Offer(
    val name: String,
    val cost: Int,
    private val commands: List<String>,
    val guiSlot: Int,
    val itemStack: ItemStack
) {

    fun commands(playerName: String) = commands.map { it.replace("{player}", playerName) }

}