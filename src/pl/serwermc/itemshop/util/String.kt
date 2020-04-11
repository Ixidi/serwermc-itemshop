package pl.serwermc.itemshop.util

import org.bukkit.ChatColor

fun String.color() = ChatColor.translateAlternateColorCodes('&', this)