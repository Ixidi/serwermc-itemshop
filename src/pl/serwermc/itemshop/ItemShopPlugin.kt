package pl.serwermc.itemshop

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.get
import pl.serwermc.itemshop.command.Commands
import pl.serwermc.itemshop.injection.Injection

class ItemShopPlugin : JavaPlugin() {

    override fun onEnable() {
        Injection.enable(this)
        Injection.get<Commands>().getAll().forEach { (k, v) -> getCommand(k)?.setExecutor(v) }
    }

}