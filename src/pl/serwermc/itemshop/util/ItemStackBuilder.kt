package pl.serwermc.itemshop.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

fun itemStack(
    material: Material,
    amount: Int = 1,
    name: String? = null,
    lore: List<String> = emptyList()
): ItemStack {
    val stack = ItemStack(material, amount)
    val meta = stack.itemMeta!!
    if (name != null) meta.setDisplayName(name.color())
    if (lore.isNotEmpty()) meta.lore = lore.map { it.color() }

    stack.itemMeta = meta
    return stack
}

@Suppress("deprecation")
fun skullItemStack(
    owner: String,
    amount: Int  = 1,
    name: String? = null,
    lore: List<String> = emptyList()
) : ItemStack {
    val stack = itemStack(Material.PLAYER_HEAD, amount, name, lore)
    val meta = stack.itemMeta as? SkullMeta ?: return stack
    meta.owner = owner

    stack.itemMeta = meta
    return stack
}