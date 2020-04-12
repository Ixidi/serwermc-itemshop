package pl.serwermc.itemshop

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.get
import pl.serwermc.itemshop.command.Commands
import pl.serwermc.itemshop.gui.GuiManager
import pl.serwermc.itemshop.injection.Injection
import pl.serwermc.itemshop.listener.Listeners

@Suppress("unused")
class ItemShopPlugin : JavaPlugin() {

    override fun onEnable() {
        Injection.enable(this)
        Injection.get<Commands>().getAll().forEach { (k, v) -> getCommand(k)?.setExecutor(v) }
        Injection.get<Listeners>().getAll().forEach { server.pluginManager.registerEvents(it, this) }
    }

    override fun onDisable() {
        Injection.get<GuiManager>().closeAll()
        Injection.disable()
    }

}