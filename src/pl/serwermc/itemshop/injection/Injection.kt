package pl.serwermc.itemshop.injection

import org.bukkit.plugin.Plugin
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import pl.serwermc.itemshop.injection.module.dataModule
import pl.serwermc.itemshop.injection.module.pluginModule

object Injection : KoinComponent {

    fun enable(plugin: Plugin) {
        startKoin { modules(modules(plugin)) }
    }

    fun disable() {
        GlobalContext.stop()
    }

    private fun modules(plugin: Plugin) = listOf(
        pluginModule(plugin),
        dataModule
    )


}